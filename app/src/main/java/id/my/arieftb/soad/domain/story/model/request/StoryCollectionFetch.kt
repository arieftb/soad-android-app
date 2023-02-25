package id.my.arieftb.soad.domain.story.model.request

import id.my.arieftb.soad.domain.common.constant.Request
import id.my.arieftb.soad.domain.common.model.RequestMapper

class StoryCollectionFetch(
    val page: String,
    val size: String,
) : RequestMapper.Header, RequestMapper.Query {
    lateinit var accessToken: String
    var useLocation: Int = 0

    override fun toHeader(): Map<String, Any> {
        return HashMap<String, Any>().apply {
            this[Request.AUTHORIZATION] = accessToken
        }
    }

    override fun toQuery(): Map<String, Any> {
        return HashMap<String, Any>().apply {
            this[Request.LOCATION] = useLocation
            this[Request.PAGE] = page
            this[Request.SIZE] = size
        }
    }
}
