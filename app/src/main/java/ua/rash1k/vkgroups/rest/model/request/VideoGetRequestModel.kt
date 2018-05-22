package ua.rash1k.vkgroups.rest.model.request

import com.google.gson.annotations.SerializedName
import com.vk.sdk.api.VKApiConst
import ua.rash1k.vkgroups.consts.ApiConstants


class VideoGetRequestModel(@SerializedName("owner_id")
                           var ownerId: Int = -1,
                           videoId: Int = 1) : BaseRequestModel() {


    @SerializedName("videos")
    private var videos: String = "${ownerId}_$videoId"


    constructor(videos: String) : this() {
        this.videos = videos
    }


    override fun onMapCreate(map: HashMap<String, String>) {
        map[VKApiConst.OWNER_ID] = ownerId.toString()
        map[ApiConstants.VIDEOS] = videos
    }
}