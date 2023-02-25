package id.my.arieftb.soad.data.common.utils

import com.google.gson.Gson
import id.my.arieftb.soad.data.common.model.ErrorRemoteResponse
import java.io.Reader

object RemoteErrorMessageUtils {
    fun toString(json: Reader): String {
        return Gson().fromJson(json, ErrorRemoteResponse::class.java).message
    }
}
