package ua.rash1k.vkgroups.rest.model.request

import com.google.gson.annotations.SerializedName
import com.vk.sdk.api.VKApiConst


class WallGetByIdRequestModel(ownerId: Int,
                              postsId: Int,
                              @SerializedName(VKApiConst.POSTS)
                              val posts: String = "${ownerId}_$postsId",
//                              @SerializedName(VKApiConst.FIELDS)
//                              val fields:String = ApiConstants.DEFAULT_WALL_BY_ID_FIELDS,
                              @SerializedName(VKApiConst.EXTENDED)
                              val extended: Int = 1) : BaseRequestModel() {


    override fun onMapCreate(map: HashMap<String, String>) {
        map[VKApiConst.POSTS] = posts
        map[VKApiConst.EXTENDED] = extended.toString()
    }
}