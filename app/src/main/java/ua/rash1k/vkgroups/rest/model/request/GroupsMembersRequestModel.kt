package ua.rash1k.vkgroups.rest.model.request

import com.google.gson.annotations.SerializedName
import com.vk.sdk.api.VKApiConst
import ua.rash1k.vkgroups.consts.ApiConstants


class GroupsMembersRequestModel(
        @SerializedName(VKApiConst.GROUP_ID)
        var groupId: String,
        @SerializedName(VKApiConst.COUNT)
        var count: Int = ApiConstants.DEFAULT_COUNT,
        @SerializedName(VKApiConst.OFFSET)
        var offset: Int,
        @SerializedName(VKApiConst.FIELDS)
        val fields: String = ApiConstants.DEFAULT_MEMBERS_FIELD) : BaseRequestModel() {

    override fun onMapCreate(map: HashMap<String, String>) {
        map[VKApiConst.GROUP_ID] = groupId
        map[VKApiConst.COUNT] = count.toString()
        map[VKApiConst.OFFSET] = offset.toString()
        map[VKApiConst.FIELDS] = fields
    }
}