package id.my.arieftb.soad.presentation.page.register

import android.content.Context
import android.content.Intent
import id.my.arieftb.soad.databinding.ActivityRegisterBinding
import id.my.arieftb.soad.presentation.base.page.BaseActivityImpl

class RegisterActivity : BaseActivityImpl<ActivityRegisterBinding>() {
    override fun initViewBinding(): ActivityRegisterBinding {
        return ActivityRegisterBinding.inflate(layoutInflater)
    }


    companion object {
        fun navigate(from: Context): Intent {
            return Intent(from, RegisterActivity::class.java)
        }
    }
}