package id.my.arieftb.soad.presentation.page.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import id.my.arieftb.soad.databinding.ActivityRegisterBinding
import id.my.arieftb.soad.presentation.base.page.BaseActivityImpl

class RegisterActivity : BaseActivityImpl<ActivityRegisterBinding>(), RegisterContract.View {
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
                    // TODO: Create Account Here
                }
            }

            labelSignIn.setOnClickListener {
                onBackPressed()
            }
        }
    }

    companion object {
        fun navigate(from: Context): Intent {
            return Intent(from, RegisterActivity::class.java)
        }
    }
}