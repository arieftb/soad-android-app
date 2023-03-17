package id.my.arieftb.soad.presentation.page.login

import id.my.arieftb.soad.presentation.common.state.UIState
import kotlinx.coroutines.flow.StateFlow

class LoginContract {
    interface View{
        fun init()
    }

    interface ViewModel {
        val logInState: StateFlow<UIState<Boolean>>

        fun submitLogin(email: String?, password: String?)
    }
}
