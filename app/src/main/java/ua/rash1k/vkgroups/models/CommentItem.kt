package ua.rash1k.vkgroups.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import ua.rash1k.vkgroups.models.attachment.ApiAttachment
import ua.rash1k.vkgroups.models.countable.Likes
import ua.rash1k.vkgroups.models.countable.Reposts


open class CommentItem : RealmObject() {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    var id = 0

    @SerializedName("from_id")
    @Expose
    var fromId = 0


    @SerializedName("date")
    @Expose
    var date = 0


    @SerializedName("text")
    @Expose
    var text = ""

//    @SerializedName("place")
//    @Expose
    var place: Place? = null


    @SerializedName("attachments")
    @Expose
    var attachments: RealmList<ApiAttachment> = RealmList()


    @SerializedName("likes")
    @Expose
    var likes: Likes? = null


    @SerializedName("reposts")
    @Expose
    var reposts: Reposts? = null


    var isFromTopic = false

    var senderName = ""

    var senderPhoto = ""

    var attachmentsString: String = ""
}