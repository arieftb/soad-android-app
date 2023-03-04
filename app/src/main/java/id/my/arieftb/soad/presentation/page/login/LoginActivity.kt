package id.my.arieftb.soad.presentation.page.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import id.my.arieftb.soad.databinding.ActivityLoginBinding
import id.my.arieftb.soad.presentation.base.page.BaseActivityImpl
import id.my.arieftb.soad.presentation.page.main.MainActivity
import id.my.arieftb.soad.presentation.page.register.RegisterActivity

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
                    startActivity(MainActivity.navigate(this@LoginActivity))
                }
            }

            labelCreateAccount.setOnClickListener {
                startActivity(RegisterActivity.navigate(this@LoginActivity))
            }
        }
    }

    companion object {
        fun navigate(from: Context): Intent {
            return Intent(from, LoginActivity::class.java)
        }
    }
}
