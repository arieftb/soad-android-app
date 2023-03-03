package id.my.arieftb.soad.data.profile.model.create

import id.my.arieftb.soad.data.common.constant.Request
import id.my.arieftb.soad.data.common.model.RequestMapper

class ProfileCreateRequest(
    val name: String,
    val email: String,
    val password: String,
) : RequestMapper.Body {
    override fun toBody(): Map<String, Any> {
        return HashMap<String, Any>().apply {
            this[Request.NAME] = name
            this[Request.EMAIL] = email
            this[Request.PASSWORD] = password
        }
    }
}
