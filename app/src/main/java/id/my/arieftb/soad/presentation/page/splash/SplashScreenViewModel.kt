package id.my.arieftb.soad.presentation.page.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.my.arieftb.soad.domain.auth.use_case.GetAuthStatusUseCase
import id.my.arieftb.soad.domain.common.model.ResultEntity
import id.my.arieftb.soad.presentation.common.state.UIState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val getLoginStatus: GetAuthStatusUseCase
) : ViewModel(), SplashScreenContract.ViewModel {
    private var _getLoginStatus: Job? = null
    private var _loginStatusState = MutableStateFlow<UIState<Boolean>>(UIState.Idle())

    override val loginStatusState: StateFlow<UIState<Boolean>>
        get() = _loginStatusState.asStateFlow()

    override fun fetchLoginStatus() {
        _getLoginStatus?.cancel()
        _loginStatusState.value = UIState.Loading(true)
        _getLoginStatus = viewModelScope.launch {
            getLoginStatus.invoke().catch {
                _loginStatusState.value = UIState.Loading(false)
                delay(1)
                _loginStatusState.value = UIState.Success(false)
            }.collect {
                _loginStatusState.value = UIState.Loading(false)
                delay(1)
                when (it) {
                    is ResultEntity.Error -> {
                        _loginStatusState.value = UIState.Success(false)
                    }
                    is ResultEntity.Failure -> {
                        _loginStatusState.value = UIState.Success(false)
                    }
                    is ResultEntity.Success -> {
                        _loginStatusState.value = UIState.Success(it.data)
                    }
                }
            }
        }
    }
}
