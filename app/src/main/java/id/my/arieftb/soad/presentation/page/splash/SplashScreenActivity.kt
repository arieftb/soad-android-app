package id.my.arieftb.soad.presentation.page.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import id.my.arieftb.soad.databinding.ActivitySplashBinding
import id.my.arieftb.soad.presentation.base.page.BaseActivityImpl
import id.my.arieftb.soad.presentation.common.state.UIState
import id.my.arieftb.soad.presentation.page.login.LoginActivity
import id.my.arieftb.soad.presentation.page.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : BaseActivityImpl<ActivitySplashBinding>(), SplashScreenContract.View {
    private val viewModel: SplashScreenViewModel by viewModels()

    override fun initViewBinding(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subscribeLoginStatus()
        fetchLoginStatus()
    }

    override fun fetchLoginStatus() {
        viewModel.fetchLoginStatus()
    }

    override fun subscribeLoginStatus() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginStatusState.collect {
                    if (it is UIState.Success) {
                        if (!it.data) {
                            delay(1000)
                            startActivity(LoginActivity.navigate(this@SplashScreenActivity))
                            finish()
                            return@collect
                        }

                        delay(1000)
                        startActivity(MainActivity.navigate(this@SplashScreenActivity))
                        finish()
                        return@collect
                    }
                }
            }
        }
    }
}
