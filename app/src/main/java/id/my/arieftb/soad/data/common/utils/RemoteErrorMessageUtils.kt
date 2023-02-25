package id.my.arieftb.soad.data.common.utils

import com.google.gson.Gson
import id.my.arieftb.soad.data.common.model.ErrorRemoteResponse
import java.io.Reader

object RemoteErrorMessageUtils {
    fun toObject(json: Reader): ErrorRemoteResponse {
        return Gson().fromJson(json, ErrorRemoteResponse::class.java)
    }
}
