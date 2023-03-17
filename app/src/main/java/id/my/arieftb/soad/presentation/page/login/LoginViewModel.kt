package id.my.arieftb.soad.presentation.page.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.my.arieftb.soad.domain.auth.use_case.LogInAuthUseCase
import id.my.arieftb.soad.domain.common.exception.EmailAttributeMissingException
import id.my.arieftb.soad.domain.common.exception.EmailWrongFormatException
import id.my.arieftb.soad.domain.common.exception.PasswordSmallerThanException
import id.my.arieftb.soad.domain.common.model.ResultEntity
import id.my.arieftb.soad.domain.common.utils.format_validation.FormatValidator
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
class LoginViewModel @Inject constructor(
    private val logInUseCase: LogInAuthUseCase
) : ViewModel(), LoginContract.ViewModel {

    private var _logInUseCase: Job? = null
    private val _logInState: MutableStateFlow<UIState<Boolean>> = MutableStateFlow(UIState.Idle())

    override val logInState: StateFlow<UIState<Boolean>>
        get() = _logInState.asStateFlow()

    override fun submitLogin(email: String?, password: String?) {
        _logInUseCase?.cancel()
        _logInState.value = UIState.Loading(true)

        if (email.isNullOrEmpty()) {
            _logInState.value = UIState.Loading(false)
            _logInState.value = UIState.Error(EmailAttributeMissingException())
            return
        }

        if (!FormatValidator.isEmail(email)) {
            _logInState.value = UIState.Loading(false)
            _logInState.value = UIState.Error(EmailWrongFormatException())
            return
        }

        if (password.isNullOrEmpty()) {
            _logInState.value = UIState.Loading(false)
            _logInState.value = UIState.Error(PasswordSmallerThanException())
            return
        }

        if (password.length < 8) {
            _logInState.value = UIState.Loading(false)
            _logInState.value = UIState.Error(PasswordSmallerThanException())
            return
        }

        _logInUseCase = viewModelScope.launch {
            logInUseCase.invoke(email, password).catch {
                emit(ResultEntity.Error(Exception(it)))
            }.collect {
                _logInState.value = UIState.Loading(false)
                delay(1)

                if (it is ResultEntity.Error) {
                    _logInState.value = UIState.Error(it.exception)
                    return@collect
                }

                if (it is ResultEntity.Failure) {
                    _logInState.value = UIState.Failure(it.message)
                    return@collect
                }

                if (it is ResultEntity.Success) {
                    _logInState.value = UIState.Success(it.data)
                    return@collect
                }
            }
        }
    }
}
