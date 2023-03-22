package id.my.arieftb.soad.presentation.page.account

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.my.arieftb.soad.R
import id.my.arieftb.soad.databinding.FragmentAccountBinding
import id.my.arieftb.soad.presentation.base.page.BaseFragmentImpl
import id.my.arieftb.soad.presentation.common.state.UIState
import id.my.arieftb.soad.presentation.page.login.LoginActivity
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AccountFragment : BaseFragmentImpl<FragmentAccountBinding>(), AccountContract.View {
    private val viewModel: AccountViewModel by viewModels()
    private var snackBar: Snackbar? = null

    override fun initViewBinding(): FragmentAccountBinding {
        return FragmentAccountBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        subscribeLogOut()
    }

    override fun init() {
        initLogout()
    }

    override fun initLogout() {
        binding?.apply {
            buttonLogout.setOnClickListener {
                logOut()
            }
        }
    }

    override fun logOut() {
        viewModel.logOut()
    }

    override fun subscribeLogOut() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.logOutState.collect {
                    if (it is UIState.Loading) {
                        onLoadingLogOut(it.isLoading)
                        return@collect
                    }

                    if (it is UIState.Success) {
                        if (!it.data) {
                            onErroLogOut()
                            return@collect
                        }

                        onSuccessLogOut()
                        return@collect
                    }

                    if (it !is UIState.Idle) {
                        onErroLogOut()
                        return@collect
                    }
                }
            }
        }
    }

    override fun onSuccessLogOut() {
        startActivity(LoginActivity.navigate(requireContext()).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
        requireActivity().finish()
    }

    override fun onErroLogOut() {
        binding?.apply {
            snackBar?.dismiss()
            snackBar = Snackbar.make(
                root,
                String.format(
                    getString(R.string.logout_message_error_template),
                    getString(R.string.logout_message_error_something_went_wrong)
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

    override fun onLoadingLogOut(isLoading: Boolean) {
        binding?.apply {
            buttonLogout.isEnabled = !isLoading
        }
    }

    companion object {
        fun navigate(): AccountFragment {
            return AccountFragment()
        }
    }
}
