package id.my.arieftb.soad.presentation.page.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.my.arieftb.soad.domain.common.exception.EmailAttributeMissingException
import id.my.arieftb.soad.domain.common.exception.NameAttributeMissingException
import id.my.arieftb.soad.domain.common.exception.PasswordSmallerThanException
import id.my.arieftb.soad.domain.common.model.ResultEntity
import id.my.arieftb.soad.domain.profile.use_case.CreateProfileUseCase
import id.my.arieftb.soad.presentation.common.state.UIState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: CreateProfileUseCase
) : ViewModel(), RegisterContract.ViewModel {
    private var _registrationUseCase: Job? = null

    private val _registerState: MutableStateFlow<UIState<Boolean>> =
        MutableStateFlow(UIState.Idle())

    override val registerState: StateFlow<UIState<Boolean>>
        get() = _registerState.asStateFlow()

    override fun submitRegistration(name: String?, email: String?, password: String?) {
        _registrationUseCase?.cancel()
        _registerState.value = UIState.Loading(true)

        if (name.isNullOrEmpty()) {
            _registerState.value = UIState.Error(NameAttributeMissingException())
            return
        }

        if (email.isNullOrEmpty()) {
            _registerState.value = UIState.Error(EmailAttributeMissingException())
            return
        }

        if (password.isNullOrEmpty()) {
            _registerState.value = UIState.Error(PasswordSmallerThanException())
            return
        }

        if (password.length < 8) {
            _registerState.value = UIState.Error(PasswordSmallerThanException())
            return
        }

        _registrationUseCase = viewModelScope.launch {
            registerUseCase.execute(name, email, password).collect {
                _registerState.value = UIState.Loading(false)
                if (it is ResultEntity.Error) {
                    _registerState.value = UIState.Error(it.exception)
                    return@collect
                }

                if (it is ResultEntity.Failure) {
                    _registerState.value = UIState.Failure(it.message)
                    return@collect
                }

                if (it is ResultEntity.Success) {
                    _registerState.value = UIState.Success(it.data)
                    return@collect
                }
            }
        }
    }

}
