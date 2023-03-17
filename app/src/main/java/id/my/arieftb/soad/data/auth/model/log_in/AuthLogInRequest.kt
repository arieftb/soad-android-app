package id.my.arieftb.soad.data.auth.model.log_in

import id.my.arieftb.soad.data.common.constant.Request
import id.my.arieftb.soad.data.common.model.RequestMapper

class AuthLogInRequest(
    val email: String,
    val password: String,
) : RequestMapper.Body {
    override fun toBody(): Map<String, Any> {
        return HashMap<String, Any>().apply {
            this[Request.EMAIL] = email
            this[Request.PASSWORD] = password
        }
    }
}
