package ua.rash1k.vkgroups.rest.model.request

import com.google.gson.annotations.SerializedName
import com.vk.sdk.api.VKApiConst
import ua.rash1k.vkgroups.consts.ApiConstants


//Для получения данных пользователя из сети
class UsersGetRequestModel(@SerializedName(VKApiConst.USER_ID)
                           var usersId: String,
                           @SerializedName(VKApiConst.FIELDS)
                           val fields: String = ApiConstants.DEFAULT_USERS_FIELDS) : BaseRequestModel() {


    override fun onMapCreate(map: HashMap<String, String>) {
        map[VKApiConst.USER_ID] = usersId
        map[VKApiConst.FIELDS] = fields
    }
}