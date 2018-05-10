package ua.rash1k.vkgroups.models.attachment.doc

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import ua.rash1k.vkgroups.models.attachment.video.Video


open class Preview :RealmObject() {

    @SerializedName("photo")
     var photo:PhotoPreview? = null

    @SerializedName("video")
     var video: Video? = null
}