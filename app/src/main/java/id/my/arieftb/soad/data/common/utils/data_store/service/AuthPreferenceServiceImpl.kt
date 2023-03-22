package id.my.arieftb.soad.data.common.utils.data_store.service

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class AuthPreferenceServiceImpl @Inject constructor(
    context: Context
) : AuthPreferenceService {

    private val Context.dataStore by preferencesDataStore(
        name = AUTH
    )

    private val dataSource = context.dataStore

    override suspend fun create(token: String): Boolean {
        dataSource.edit {
            it[stringPreferencesKey(AUTH_TOKEN)] = token
        }

        return fetch().isNotEmpty()
    }

    override suspend fun fetch(): String {
        return dataSource.data.map {
            it[stringPreferencesKey(AUTH_TOKEN)] ?: ""
        }.first()
    }

    override suspend fun delete(): Boolean {
        dataSource.edit {
            it.remove(stringPreferencesKey(AUTH_TOKEN))
        }

        return fetch().isEmpty()
    }

    companion object {
        private const val AUTH = "auth"
        private const val AUTH_TOKEN = "auth_token"
    }
}
