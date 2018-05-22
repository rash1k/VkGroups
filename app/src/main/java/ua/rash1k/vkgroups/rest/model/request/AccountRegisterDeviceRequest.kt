package ua.rash1k.vkgroups.rest.model.request

import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.annotations.SerializedName
import org.json.JSONObject
import ua.rash1k.vkgroups.consts.ApiConstants


class AccountRegisterDeviceRequest(
        @SerializedName(ApiConstants.TOKEN)
        val token: String? = FirebaseInstanceId.getInstance().token,
        @SerializedName(ApiConstants.DEVICE_MODEL)
        val deviceModel: String = "android",
        @SerializedName(ApiConstants.DEVICE_ID)
        val deviceId: String,
        @SerializedName(ApiConstants.SYSTEM_VERSION)
        val systemVersion: String = "22",
        @SerializedName(ApiConstants.SETTINGS)
        val settings: JSONObject = ApiConstants.getDefaultPushSettings()) : BaseRequestModel() {


    override fun onMapCreate(map: HashMap<String, String>) {
        if (token != null) {
            map[ApiConstants.TOKEN] = token
        }
        map[ApiConstants.DEVICE_MODEL] = deviceModel
        map[ApiConstants.DEVICE_ID] = deviceId
        map[ApiConstants.SYSTEM_VERSION] = systemVersion
        map[ApiConstants.SETTINGS] = settings.toString()
    }
}