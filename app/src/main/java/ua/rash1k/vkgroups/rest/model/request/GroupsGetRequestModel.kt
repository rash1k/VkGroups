package ua.rash1k.vkgroups.rest.model.request

import com.google.gson.annotations.SerializedName
import com.vk.sdk.api.VKApiConst
import ua.rash1k.vkgroups.CurrentUser


class GroupsGetRequestModel(@SerializedName(VKApiConst.USER_ID)
                            val userId: String = CurrentUser.getUserId() ?: "",
                            @SerializedName(VKApiConst.EXTENDED)
                            val extended: Int = 1)
    : BaseRequestModel() {


    override fun onMapCreate(map: HashMap<String, String>) {
    }
}