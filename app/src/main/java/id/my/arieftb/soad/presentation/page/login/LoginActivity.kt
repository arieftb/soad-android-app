package id.my.arieftb.soad.presentation.page.login

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.google.android.recaptcha.Recaptcha
import com.google.android.recaptcha.RecaptchaAction
import com.google.android.recaptcha.RecaptchaClient
import id.my.arieftb.soad.BuildConfig
import id.my.arieftb.soad.databinding.ActivityLoginBinding
import id.my.arieftb.soad.presentation.base.page.BaseActivityImpl
import id.my.arieftb.soad.presentation.page.register.RegisterActivity
import kotlinx.coroutines.launch

class LoginActivity : BaseActivityImpl<ActivityLoginBinding>(), LoginContract.View {

    private var recaptchaClient: RecaptchaClient? = null

    override fun initViewBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun init() {
        binding?.apply {
            lifecycleScope.launch {
                Recaptcha.getClient(application, BuildConfig.RECAPTCHA_KEY)
                    .onSuccess {
                        Log.d("RECAPTCHA INIT", "init: success")
                        recaptchaClient = it
                    }.onFailure {
                        it.printStackTrace()
                    }

                buttonSignIn.setOnClickListener {
                    lifecycleScope.launch {
                        recaptchaClient?.execute(RecaptchaAction.LOGIN)?.onSuccess {
                            Log.d("RECAPTCHA", "init Token: $it ")
                        }?.onFailure {
                            it.printStackTrace()
                        }
                    }
                }
//                if (fieldEmail.isValid && fieldPassword.isValid) {
//                    startActivity(MainActivity.navigate(this@LoginActivity))
//                }

            }

            labelCreateAccount.setOnClickListener {
                startActivity(RegisterActivity.navigate(this@LoginActivity))
            }
        }
    }
}
