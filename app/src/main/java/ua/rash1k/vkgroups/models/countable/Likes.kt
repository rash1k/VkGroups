package ua.rash1k.vkgroups.models.countable

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class Likes : RealmObject() {
    @SerializedName("count")
    var count: Int = 0//202
    @SerializedName("user_likes")
    var userLikes: Int = 0 //0
    @SerializedName("can_like")
    var canLike: Int = 0 //1
    @SerializedName("can_publish")
    var canPublish: Int = 0//1

    fun isUserLikes(): Boolean = userLikes == 1


}