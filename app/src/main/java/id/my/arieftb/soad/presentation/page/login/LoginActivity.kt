package id.my.arieftb.soad.presentation.page.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.my.arieftb.soad.R
import id.my.arieftb.soad.databinding.ActivityLoginBinding
import id.my.arieftb.soad.domain.common.exception.EmailAttributeMissingException
import id.my.arieftb.soad.domain.common.exception.EmailWrongFormatException
import id.my.arieftb.soad.domain.common.exception.PasswordSmallerThanException
import id.my.arieftb.soad.presentation.base.page.BaseActivityImpl
import id.my.arieftb.soad.presentation.common.state.UIState
import id.my.arieftb.soad.presentation.page.main.MainActivity
import id.my.arieftb.soad.presentation.page.register.RegisterActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivityImpl<ActivityLoginBinding>(), LoginContract.View {

    private val viewModel: LoginViewModel by viewModels()
    private var snackBar: Snackbar? = null

    override fun initViewBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun init() {
        binding?.apply {
            buttonSignIn.setOnClickListener {
                if (fieldEmail.isValid && fieldPassword.isValid) {
                    submitLogin()
                }
            }

            labelCreateAccount.setOnClickListener {
                startActivity(RegisterActivity.navigate(this@LoginActivity))
            }
        }

        subscribeLogin()
    }

    override fun submitLogin() {
        binding?.apply {
            viewModel.submitLogin(
                fieldEmail.editText.text.toString(),
                fieldPassword.editText.text.toString()
            )
        }
    }

    override fun subscribeLogin() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.logInState.collect {
                    when (it) {
                        is UIState.Error -> {
                            if (it.exception is EmailWrongFormatException) {
                                onFailureLogin(
                                    getString(R.string.error_message_field_email_is_in_invalid_format)
                                )
                                return@collect
                            }

                            if (it.exception is EmailAttributeMissingException) {
                                onFailureLogin(
                                    String.format(
                                        getString(R.string.error_message_field_cannot_be_empty),
                                        getString(R.string.label_field_email)
                                    )
                                )
                                return@collect
                            }

                            if (it.exception is PasswordSmallerThanException) {
                                onFailureLogin(
                                    String.format(
                                        getString(R.string.error_message_field_cannot_be_less_than_limit),
                                        getString(R.string.label_field_password),
                                        8
                                    )
                                )
                                return@collect
                            }

                            onErrorLogin()
                        }
                        is UIState.Failure -> {
                            onFailureLogin(it.message)
                        }
                        is UIState.Idle -> {
                            onLoadingLogin(false)
                        }
                        is UIState.Loading -> {
                            onLoadingLogin(it.isLoading)
                        }
                        is UIState.Success -> {
                            if (!it.data) {
                                onErrorLogin()
                                return@collect
                            }

                            onSuccessLogin()
                        }
                    }
                }
            }
        }
    }

    override fun onLoadingLogin(isLoading: Boolean) {
        binding?.apply {
            buttonSignIn.isEnabled = !isLoading
            if (isLoading) {
                buttonSignIn.showProgress()
            } else {
                buttonSignIn.hideProgress()
            }
        }
    }

    override fun onErrorLogin() {
        binding?.apply {
            snackBar?.dismiss()
            snackBar = Snackbar.make(
                root,
                String.format(
                    getString(R.string.login_message_error_template),
                    getString(R.string.login_message_error_something_went_wrong)
                ),
                Snackbar.LENGTH_INDEFINITE
            ).apply {
                setAction(getString(R.string.button_ok)) {
                    snackBar?.dismiss()
                }
                setActionTextColor(ContextCompat.getColor(context, R.color.turquoise))
            }.also {
                it.show()
            }
        }
    }

    override fun onFailureLogin(message: String) {
        binding?.apply {
            snackBar?.dismiss()
            snackBar = Snackbar.make(
                root,
                String.format(
                    getString(R.string.login_message_error_template),
                    message
                ),
                Snackbar.LENGTH_INDEFINITE
            ).apply {
                setAction(getString(R.string.button_ok)) {
                    snackBar?.dismiss()
                }
                setActionTextColor(ContextCompat.getColor(context, R.color.turquoise))
            }.also {
                it.show()
            }
        }
    }

    override fun onSuccessLogin() {
        lifecycleScope.launch {
            delay(1000)
            startActivity(MainActivity.navigate(this@LoginActivity).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
            finish()
        }
    }

    companion object {
        fun navigate(from: Context): Intent {
            return Intent(from, LoginActivity::class.java)
        }
    }
}
