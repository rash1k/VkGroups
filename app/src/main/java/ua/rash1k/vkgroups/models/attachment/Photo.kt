package ua.rash1k.vkgroups.models.attachment

import com.google.gson.annotations.SerializedName
import com.vk.sdk.api.model.VKAttachments
import io.realm.RealmObject


open class Photo : RealmObject(), Attachment {

    @SerializedName("id")
    internal var id: Int = 0 //456239461

    @SerializedName("count")
    var count = 0

    @SerializedName("album_id")
    var albumId: Int = 0 //-7
    @SerializedName("owner_id")
    var ownerId: Int = 0 //-86529522
//        get() = Math.abs(field)
    @SerializedName("user_id")
    var userId: Int = 0 //100
    @SerializedName("photo_75")
    var photo75: String? = null //https://pp.userap...5a3/_LwF-Hsv4B4.jpg
    @SerializedName("photo_130")
    var photo130: String? = null //https://pp.userap...5a4/k8a0uXCbkMY.jpg
    @SerializedName("photo_604")
    var photo604: String? = null //https://pp.userap...5a5/TKjIcburlwc.jpg
    @SerializedName("photo_807")
    var photo807: String? = null //https://pp.userap...5a6/tdN8S-RaCQw.jpg
    @SerializedName("photo_1280")
    var photo1280: String? = null //https://pp.userap...5a7/E0vOE2dnkNY.jpg
    @SerializedName("width")
    var width: Int = 0 //1280
    @SerializedName("height")
    var height: Int = 0 //853
    @SerializedName("text")
    var text: String? = null
    @SerializedName("date")
    var date: Int = 0 //1489591865
    @SerializedName("access_key")
    var accessKey: String? = null


    override fun getType(): String {
        return VKAttachments.TYPE_PHOTO
    }

    override fun getId(): Int {
        return id
    }
}