package ua.rash1k.vkgroups.models.countable

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class Comments : RealmObject() {
    @SerializedName("count")
    var count: Int = 0 //25
    @SerializedName("can_post")
    var canPost: Int = 0//1
}