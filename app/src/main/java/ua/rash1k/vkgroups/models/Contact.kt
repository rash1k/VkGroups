package ua.rash1k.vkgroups.models

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject


open class Contact : RealmObject() {

    companion object {
        const val USER_ID = "user_id"
        const val DESC = "desc"
        const val PHOTO = "photo_100"
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
    }

    @SerializedName(USER_ID)
    var userId: Int = 0

    @SerializedName(DESC)
    lateinit var desc: String
}