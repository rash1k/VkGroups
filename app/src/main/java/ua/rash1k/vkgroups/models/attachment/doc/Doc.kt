package ua.rash1k.vkgroups.models.attachment.doc

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.vk.sdk.api.model.VKAttachments
import io.realm.RealmObject
import ua.rash1k.vkgroups.models.attachment.Attachment


open class Doc : RealmObject(), Attachment {
    @SerializedName("id")
    @Expose
    internal var id: Int = 0
    @SerializedName("owner_id")
    @Expose
    var ownerId: Int = 0
    @SerializedName("title")
    @Expose
    lateinit var title: String
    @SerializedName("size")
    @Expose
    var size: Long = 0
    @SerializedName("ext")
    @Expose
    lateinit var ext: String
    @SerializedName("url")
    @Expose
    lateinit var url: String
    @SerializedName("date")
    @Expose
    var date: Int = 0
    @SerializedName("type")
    @Expose
    var type: Int = 0
    @SerializedName("preview")
    @Expose
     var preview: Preview? = null
    @SerializedName("access_key")
    @Expose
    lateinit var accessKey: String

    override fun getId(): Int {
        return id
    }

    override fun getType(): String {
        return VKAttachments.TYPE_DOC
    }
}