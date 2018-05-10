package ua.rash1k.vkgroups.models.attachment.video

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.vk.sdk.api.model.VKAttachments
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import ua.rash1k.vkgroups.models.attachment.Attachment


open class Video : RealmObject(), Attachment {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private var id: Int = 0
    @SerializedName("owner_id")
    @Expose
    var ownerId: Int = 0
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("duration")
    @Expose
    var duration: Long = 0
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("date")
    @Expose
    var date: Int = 0
    @SerializedName("views")
    @Expose
    var views: Int = 0
    @SerializedName("comments")
    @Expose
    var comments: Int = 0
    @SerializedName("photo_130")
    @Expose
    var photo130: String? = null
    @SerializedName("photo_320")
    @Expose
    var photo320: String? = null
    @SerializedName("photo_800")
    @Expose
    var photo800: String? = null
    @SerializedName("player")
    @Expose
    var player: String? = null
    @SerializedName("access_key")
    @Expose
    var accessKey: String? = null
    @SerializedName("can_add")
    @Expose
    var canAdd: Int = 0

    @SerializedName("files")
    @Expose
    var files: Files? = null


    override fun getId(): Int {
        return id
    }

    override fun getType(): String {
        return VKAttachments.TYPE_VIDEO
    }

}