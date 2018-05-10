package ua.rash1k.vkgroups.rest.model.request

import com.google.gson.annotations.SerializedName
import ua.rash1k.vkgroups.consts.ApiConstants


class VideoGetRequestModel(@SerializedName("owner_id")
                           var ownerId: Int = -1,
                           videoId: Int = 1) : BaseRequestModel() {


    @SerializedName("videos")
    var videos: String = initVideos(ownerId, videoId)


    constructor(ownerId: String, videoId: Int) : this() {
        this.videos = ownerId + videoId
    }

    constructor(videos: String) : this() {
        this.videos = videos
    }


    override fun onMapCreate(map: HashMap<String, String>) {
        map[ApiConstants.VIDEOS] = videos
    }

    private fun initVideos(ownerId: Int, videoId: Int): String {
        return if (ownerId.toString().startsWith("-", false)) {
            "-${ownerId}_$videoId"
        } else "${ownerId}_$videoId"
    }
}