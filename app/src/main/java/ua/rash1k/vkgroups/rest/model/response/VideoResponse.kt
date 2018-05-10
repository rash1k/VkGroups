package ua.rash1k.vkgroups.rest.model.response

import com.google.gson.annotations.SerializedName
import com.vk.sdk.api.VKApiConst
import ua.rash1k.vkgroups.models.attachment.video.Video


class VideoResponse {

    @SerializedName(VKApiConst.COUNT)
    var count = 0

    @SerializedName("items")
    var items: List<Video> = arrayListOf()

}