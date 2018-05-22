package ua.rash1k.vkgroups.rest.model.request

import com.google.gson.annotations.SerializedName
import com.vk.sdk.api.VKApiConst
import ua.rash1k.vkgroups.consts.ApiConstants


class WallGetCommentsRequestModel(@SerializedName(VKApiConst.OWNER_ID)
                                  var ownerId: Int,

                                  @SerializedName(VKApiConst.POST_ID)
                                  var postId: Int,

                                  @SerializedName(ApiConstants.NEED_LIKES)
                                  var needLikes: Int = 1,

                                  @SerializedName(VKApiConst.COUNT)
                                  var count: Int = ApiConstants.DEFAULT_COUNT,

                                  @SerializedName(VKApiConst.OFFSET)
                                  var offset: Int,

                                  @SerializedName(VKApiConst.EXTENDED)
                                  private val extended: Int = 1) : BaseRequestModel() {


    override fun onMapCreate(map: HashMap<String, String>) {
        map[VKApiConst.OWNER_ID] = ownerId.toString()
        map[VKApiConst.POST_ID] = postId.toString()
        map[VKApiConst.COUNT] = count.toString()
        map[VKApiConst.OFFSET] = offset.toString()
        map[VKApiConst.EXTENDED] = extended.toString()
        map[ApiConstants.NEED_LIKES] = needLikes.toString()
    }
}