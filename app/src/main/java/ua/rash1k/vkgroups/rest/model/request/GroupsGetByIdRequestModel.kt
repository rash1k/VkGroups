package ua.rash1k.vkgroups.rest.model.request

import com.google.gson.annotations.SerializedName
import com.vk.sdk.api.VKApiConst
import ua.rash1k.vkgroups.consts.ApiConstants


class GroupsGetByIdRequestModel(groupId: String,
                                @SerializedName(VKApiConst.FIELDS)
                                val fields: String = ApiConstants.DEFAULT_GROUP_FIELDS)
    : BaseRequestModel() {

    @SerializedName(VKApiConst.GROUP_ID)

    var groupId: String = groupId
        set(value) :Unit {
            if (groupId[0] !in 'a'..'z') {
                field = Math.abs(value.toInt()).toString()
            }
        }

    init {
        if (groupId[0] !in 'a'..'z' && groupId[0] !in 'A'..'Z') {
            this.groupId = Math.abs(groupId.toInt()).toString()
        }
    }

    override fun onMapCreate(map: HashMap<String, String>) {
        map[VKApiConst.GROUP_ID] = groupId
        map[VKApiConst.FIELDS] = fields
    }
}