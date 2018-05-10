package ua.rash1k.vkgroups.models.attachment

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.vk.sdk.api.model.VKAttachments
import io.realm.RealmObject


open class Audio : RealmObject(), Attachment {
    @SerializedName("id")
    @Expose
    internal var id: Int = 0
    @SerializedName("owner_id")
    @Expose
    var ownerId: Int = 0
    @SerializedName("artist")
    @Expose
    lateinit var artist: String
    @SerializedName("title")
    @Expose
    lateinit var title: String
    @SerializedName("duration")
    @Expose
    var duration: Long = 0
    @SerializedName("date")
    @Expose
    var date: Int = 0
    @SerializedName("url")
    @Expose
    lateinit var url: String
    @SerializedName("lyrics_id")
    @Expose
    var lyricsId: Int = 0
    @SerializedName("genre_id")
    @Expose
    var genreId: Int = 0

    override fun getId(): Int {
        return id
    }

    override fun getType(): String {
        return VKAttachments.TYPE_AUDIO
    }
}