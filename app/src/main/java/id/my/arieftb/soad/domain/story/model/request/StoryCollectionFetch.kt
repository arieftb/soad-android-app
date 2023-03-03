package id.my.arieftb.soad.domain.story.model.request

import id.my.arieftb.soad.data.common.constant.Request
import id.my.arieftb.soad.data.common.model.RequestMapper

class StoryCollectionFetch(
    val page: Int,
    val size: Int,
    val accessToken: String? = null,
    val location: Boolean = false,
) : RequestMapper.Header, RequestMapper.Query {

    override fun toHeader(): Map<String, Any> {
        return HashMap<String, Any>().apply {
            this[Request.AUTHORIZATION] = accessToken!!
        }
    }

    override fun toQuery(): Map<String, Any> {
        return HashMap<String, Any>().apply {
            this[Request.LOCATION] = location
            this[Request.PAGE] = page
            this[Request.SIZE] = size
        }
    }
}
