package id.my.arieftb.soad.presentation.page.splash

import android.annotation.SuppressLint
import id.my.arieftb.soad.databinding.ActivitySplashBinding
import id.my.arieftb.soad.presentation.base.page.BaseActivityImpl

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : BaseActivityImpl<ActivitySplashBinding>() {

    override fun initViewBinding(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }
}