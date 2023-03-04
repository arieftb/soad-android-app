package id.my.arieftb.soad.presentation.common.state

sealed class UIState<out T> private constructor() {
    class Loading(val isLoading: Boolean) : UIState<Nothing>()
    class Error(val exception: Exception) : UIState<Nothing>()
    class Failure(val message: String) : UIState<Nothing>()
    class Success<out T : Any>(val data: T) : UIState<T>()
    class Idle : UIState<Nothing>()
}
