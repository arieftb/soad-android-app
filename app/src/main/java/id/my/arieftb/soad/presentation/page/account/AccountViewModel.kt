package id.my.arieftb.soad.presentation.page.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.my.arieftb.soad.domain.auth.use_case.LogOutAuthUseCase
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
class AccountViewModel @Inject constructor(
    private val logOutUseCase: LogOutAuthUseCase
) : ViewModel(), AccountContract.ViewModel {
    private var _logOutUseCase: Job? = null
    private var _logOutState: MutableStateFlow<UIState<Boolean>> = MutableStateFlow(UIState.Idle())

    override val logOutState: StateFlow<UIState<Boolean>>
        get() = _logOutState.asStateFlow()

    override fun logOut() {
        _logOutUseCase?.cancel()

        _logOutState.value = UIState.Loading(true)
        _logOutUseCase = viewModelScope.launch {
            logOutUseCase.invoke().catch {
                emit(ResultEntity.Error(Exception(it)))
            }.collect {
                _logOutState.value = UIState.Loading(false)
                delay(1)
                if (it is ResultEntity.Error) {
                    _logOutState.value = UIState.Success(false)
                    return@collect
                }

                if (it is ResultEntity.Failure) {
                    _logOutState.value = UIState.Success(false)
                    return@collect
                }

                if (it is ResultEntity.Success) {
                    _logOutState.value = UIState.Success(it.data)
                    return@collect
                }
            }
        }
    }
}
