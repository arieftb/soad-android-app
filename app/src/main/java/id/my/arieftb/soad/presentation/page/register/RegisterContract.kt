package id.my.arieftb.soad.presentation.page.register

import id.my.arieftb.soad.presentation.common.state.UIState
import kotlinx.coroutines.flow.StateFlow

interface RegisterContract {
    interface View {
        fun init()
        fun submitRegistration()
        fun subscribeRegistration()
        fun onLoadingRegistration(isLoading: Boolean)
        fun onErrorRegistration()
        fun onFailureRegistration(message: String)
        fun onSuccessRegistration()
    }

    interface ViewModel {
        val registerState: StateFlow<UIState<Boolean>>
        fun submitRegistration(name: String?, email: String?, password: String?)
    }
}
