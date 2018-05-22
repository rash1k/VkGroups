package ua.rash1k.vkgroups.rest.model.request

import com.google.gson.annotations.SerializedName
import com.vk.sdk.api.VKApiConst
import ua.rash1k.vkgroups.consts.ApiConstants


class BoardGetRequestModel(groupId: Int,
                           @SerializedName("count")
                           var count: Int = ApiConstants.DEFAULT_COUNT,
                           @SerializedName(VKApiConst.OFFSET)
                           var offset: Int = 0) : BaseRequestModel() {


    /*@SerializedName(VKApiConst.GROUP_ID)
    var groupId: Int = abs(groupId)
        set(value) {
            field = abs(value)
        }*/
    @SerializedName(VKApiConst.GROUP_ID)
    var groupId: Int = Math.abs(groupId)


    override fun onMapCreate(map: HashMap<String, String>) {
        map[VKApiConst.GROUP_ID] = groupId.toString()
        map[VKApiConst.OFFSET] = offset.toString()
        map[VKApiConst.COUNT] = count.toString()
    }
}