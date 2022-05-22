package id.my.arieftb.soad.presentation.page.login

import id.my.arieftb.soad.databinding.ActivityLoginBinding
import id.my.arieftb.soad.presentation.base.page.BaseActivityImpl

class LoginActivity : BaseActivityImpl<ActivityLoginBinding>() {
    override fun initViewBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }
}