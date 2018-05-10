package ua.rash1k.vkgroups.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.vk.sdk.api.model.Identifiable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class Topic : RealmObject(), Identifiable {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    internal var id: Int = 0

    @SerializedName("title")
    @Expose
    lateinit var title: String


    @SerializedName("comments")
    @Expose
    var comments = 0


    var groupId = 0

    override fun getId(): Int {
        return id
    }


}