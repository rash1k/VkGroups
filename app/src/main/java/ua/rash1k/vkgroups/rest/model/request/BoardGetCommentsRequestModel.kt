package ua.rash1k.vkgroups.rest.model.request

import com.google.gson.annotations.SerializedName
import com.vk.sdk.api.VKApiConst
import ua.rash1k.vkgroups.consts.ApiConstants


class BoardGetCommentsRequestModel(@SerializedName(ApiConstants.TOPIC_ID)
                                   val topicId: Int,
                                   groupId: Int,
                                   @SerializedName(ApiConstants.NEED_LIKES)
                                   val needLikes: Int = 1,
                                   @SerializedName(VKApiConst.OFFSET)
                                   val offset: Int,
                                   @SerializedName(VKApiConst.COUNT)
                                   val count: Int = ApiConstants.DEFAULT_COUNT,
                                   @SerializedName(VKApiConst.EXTENDED)
                                   val extended: Int = 1)
    : BaseRequestModel() {

    @SerializedName(VKApiConst.GROUP_ID)
    private val groupId: Int = Math.abs(groupId)

    override

    fun onMapCreate(map: HashMap<String, String>) {
        map[ApiConstants.TOPIC_ID] = topicId.toString()
        map[VKApiConst.GROUP_ID] = groupId.toString()
        map[ApiConstants.NEED_LIKES] = needLikes.toString()
        map[VKApiConst.OFFSET] = offset.toString()
        map[VKApiConst.COUNT] = count.toString()
        map[VKApiConst.EXTENDED] = extended.toString()
    }
}
