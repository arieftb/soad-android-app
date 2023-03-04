package id.my.arieftb.soad.presentation.page.register

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
import id.my.arieftb.soad.databinding.ActivityRegisterBinding
import id.my.arieftb.soad.presentation.base.page.BaseActivityImpl
import id.my.arieftb.soad.presentation.common.state.UIState
import id.my.arieftb.soad.presentation.page.login.LoginActivity
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : BaseActivityImpl<ActivityRegisterBinding>(), RegisterContract.View {

    private val viewModel: RegisterViewModel by viewModels()
    private var snackBar: Snackbar? = null
    override fun initViewBinding(): ActivityRegisterBinding {
        return ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun init() {
        binding?.apply {
            buttonCreate.setOnClickListener {
                if (fieldName.isValid && fieldEmail.isValid && fieldPassword.isValid) {
                    submitRegistration()
                }
            }

            labelSignIn.setOnClickListener {
                onBackPressed()
            }
        }

        subscribeRegistration()
    }

    override fun submitRegistration() {
        binding?.apply {
            viewModel.submitRegistration(
                fieldName.editText.text.toString(),
                fieldEmail.editText.text.toString(),
                fieldPassword.editText.text.toString()
            )
        }
    }

    override fun subscribeRegistration() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerState.collect {
                    when (it) {
                        is UIState.Error -> {
                            onErrorRegistration()
                        }
                        is UIState.Failure -> {
                            onFailureRegistration(it.message)
                        }
                        is UIState.Loading -> {
                            onLoadingRegistration(it.isLoading)
                        }
                        is UIState.Success -> {
                            if (!it.data) {
                                onErrorRegistration()
                                return@collect
                            }

                            onSuccessRegistration()
                        }
                        is UIState.Idle -> {
                            onLoadingRegistration(false)
                        }
                    }
                }
            }
        }
    }

    override fun onLoadingRegistration(isLoading: Boolean) {
        binding?.apply {
            buttonCreate.isEnabled = !isLoading
        }
    }

    override fun onErrorRegistration() {
        binding?.apply {
            snackBar = Snackbar.make(
                root,
                String.format(
                    getString(R.string.register_message_error_template),
                    getString(R.string.register_message_error_something_went_wrong)
                ),
                Snackbar.LENGTH_INDEFINITE
            ).apply {
                setAction(String.format(getString(R.string.button_ok))) {
                    snackBar?.dismiss()
                }
                setActionTextColor(ContextCompat.getColor(context, R.color.turquoise))
            }.also {
                it.show()
            }
        }
    }

    override fun onFailureRegistration(message: String) {
        binding?.apply {
            snackBar = Snackbar.make(
                root,
                String.format(
                    getString(R.string.register_message_error_template),
                    message
                ),
                Snackbar.LENGTH_INDEFINITE
            ).apply {
                setAction(String.format(getString(R.string.button_ok))) {
                    snackBar?.dismiss()
                }
                setActionTextColor(ContextCompat.getColor(context, R.color.turquoise))
            }.also {
                it.show()
            }
        }
    }

    override fun onSuccessRegistration() {
        binding?.apply {
            snackBar = Snackbar.make(
                root,
                String.format(
                    getString(R.string.register_message_success)
                ),
                Snackbar.LENGTH_INDEFINITE
            ).apply {
                setAction(String.format(getString(R.string.button_ok))) {
                    snackBar?.dismiss()
                    startActivity(LoginActivity.navigate(this@RegisterActivity).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    })
                    finish()
                }
                setActionTextColor(ContextCompat.getColor(context, R.color.turquoise))
            }.also {
                it.show()
            }
        }
    }

    companion object {
        fun navigate(from: Context): Intent {
            return Intent(from, RegisterActivity::class.java)
        }
    }
}
