package id.my.arieftb.soad.data.common.utils.data_store.service

interface AuthPreferenceService {
    suspend fun create(token: String): Boolean

    suspend fun fetch(): String
}
