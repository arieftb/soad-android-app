package id.my.arieftb.soad.presentation.page.login

import android.os.Bundle
import id.my.arieftb.soad.databinding.ActivityLoginBinding
import id.my.arieftb.soad.presentation.base.page.BaseActivityImpl

class LoginActivity : BaseActivityImpl<ActivityLoginBinding>(), LoginContract.View {
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
                    // TODO: Do Login Here
                }
            }

            labelCreateAccount.setOnClickListener {
                // TODO: Do Navigate to Register Activity
            }
        }
    }
}