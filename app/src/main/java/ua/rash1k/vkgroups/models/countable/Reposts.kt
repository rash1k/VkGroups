package ua.rash1k.vkgroups.models.countable

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class Reposts : RealmObject() {
    @SerializedName("count")
    var count: Int = 0 //11
    @SerializedName("user_reposted")
    var userReposted: Int = 0 //0
}