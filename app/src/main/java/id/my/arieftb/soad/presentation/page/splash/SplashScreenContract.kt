package id.my.arieftb.soad.presentation.page.splash

import id.my.arieftb.soad.presentation.common.state.UIState
import kotlinx.coroutines.flow.StateFlow

interface SplashScreenContract {
    interface ViewModel {
        val loginStatusState: StateFlow<UIState<Boolean>>

        fun fetchLogInStatus()
    }
}
