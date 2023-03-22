package id.my.arieftb.soad.presentation.page.account

import id.my.arieftb.soad.presentation.common.state.UIState
import kotlinx.coroutines.flow.StateFlow

interface AccountContract {
    interface ViewModel {
        val logOutState: StateFlow<UIState<Boolean>>
        fun logOut()
    }

    interface View {
        fun init()
        fun initLogout()
        fun logOut()
        fun subscribeLogOut()
        fun onSuccessLogOut()
        fun onErroLogOut()
        fun onLoadingLogOut(isLoading: Boolean)
    }
}
