package ua.rash1k.vkgroups.models.attachment

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.vk.sdk.api.model.VKAttachments
import io.realm.RealmObject


open class Link : RealmObject(), Attachment {

    @SerializedName("url")
    @Expose
     var url: String = ""
    @SerializedName("title")
    @Expose
     var title: String = ""

    @SerializedName("name")
    @Expose
     var name: String? = null

    @SerializedName("caption")
    @Expose
    var caption: String? =null

    @SerializedName("description")
    @Expose
     var description: String = ""

    @SerializedName("photo")
    @Expose
     var photo: Photo? =null

    @SerializedName("is_external")
    @Expose
    var isExternal: Int = 0

    override fun getId(): Int {
        return 0
    }

    override fun getType(): String {
        return VKAttachments.TYPE_LINK
    }
}