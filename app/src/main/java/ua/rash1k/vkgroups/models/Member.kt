package ua.rash1k.vkgroups.models

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open  class Member(profile: Profile? = null) : RealmObject() {

//    constructor(){}

    companion object {
        const val ID = "id"
        const val GROUP_ID = "groupId"
        const val PHOTO = "photo_100"
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
    }

    @PrimaryKey
    @SerializedName(ID)
    var id: Int = 0

    @SerializedName(GROUP_ID)
    var group_id = 0

    @SerializedName(PHOTO)
     var photo: String


    @SerializedName(FIRST_NAME)
    var firstName: String

    @SerializedName(LAST_NAME)
    var lastName: String

    init {
        id = profile?.id ?: 0
        photo = profile?.photo100 ?: ""
        firstName = profile?.firstName ?: ""
        lastName = profile?.lastName ?: ""
    }

    fun getFullName(): String = "$firstName $lastName"

}