package id.my.arieftb.soad.presentation.page.account

import id.my.arieftb.soad.presentation.common.state.UIState
import kotlinx.coroutines.flow.StateFlow

interface AccountContract {
    interface ViewModel {
        val logOutState: StateFlow<UIState<Boolean>>
        fun logOut()
    }
}
