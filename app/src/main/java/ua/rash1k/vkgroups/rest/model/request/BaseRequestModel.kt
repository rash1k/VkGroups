package ua.rash1k.vkgroups.rest.model.request

import com.google.gson.annotations.SerializedName
import com.vk.sdk.api.VKApiConst
import ua.rash1k.vkgroups.CurrentUser
import ua.rash1k.vkgroups.consts.ApiConstants


abstract class BaseRequestModel(@SerializedName(VKApiConst.VERSION)
                                private val version: Double = ApiConstants.DEFAULT_VERSION,
                                @SerializedName(VKApiConst.ACCESS_TOKEN)
                                private val accessToken: String? = CurrentUser.getAccessToken()) {


    fun toMap(): HashMap<String, String> {
        val map = hashMapOf<String, String>()
        map[VKApiConst.VERSION] = version.toString()
        if (accessToken != null) map[VKApiConst.ACCESS_TOKEN] = accessToken

        onMapCreate(map)

        return map
    }

    abstract fun onMapCreate(map: HashMap<String, String>)
}