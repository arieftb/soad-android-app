package id.my.arieftb.soad.data.auth.model.log_in

data class AuthLogInRemoteResponse(
    val error: Boolean,
    val loginResult: LoginResult?,
    val message: String
)
