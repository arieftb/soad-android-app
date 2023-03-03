package id.my.arieftb.soad.data.common.exception

class HTTPException(
    val code: Int,
    override val message: String?
) : Exception(message)
