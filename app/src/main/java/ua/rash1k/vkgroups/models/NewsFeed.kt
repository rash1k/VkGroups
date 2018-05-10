package ua.rash1k.vkgroups.models

import com.google.gson.annotations.SerializedName
import com.vk.sdk.api.model.VKAttachments
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import ua.rash1k.vkgroups.models.attachment.ApiAttachment
import ua.rash1k.vkgroups.models.countable.Comments
import ua.rash1k.vkgroups.models.countable.Likes
import ua.rash1k.vkgroups.models.countable.Reposts

const val TAG = "NewsFeed"

class NewsFeed(@SerializedName("response")
               var response: Response = Response())

class Response(
        @SerializedName("items") var items: List<News> = listOf(),
        @SerializedName("profiles") var profiles: List<Profile> = listOf(),
        @SerializedName("groups") var groups: List<Group> = listOf(),
        @SerializedName("next_from") var nextFrom: String = "") {

    fun getSender(sourceId: Int, type: String): Owner {
        when (type) {
            "post" ->
                if (sourceId < 0) {
                    return groups.find { it.id == Math.abs(sourceId) }!!
                }
        }
        throw NoSuchElementException("No Such Element ")
    }

    fun getSignedFullName(signerId: Int, type: String): String {
        return when (type) {
            "post" -> return profiles.find { it.id == signerId }?.getFullName() ?: ""
            else -> ""
        }
    }


    private fun getAllSenders(): List<Owner> {
        val all = arrayListOf<Owner>()
        all.addAll(profiles)
        all.addAll(groups)
        return all
    }

}

open class News(
        @SerializedName("id")
        @PrimaryKey
        var id: Int = 0,
        @SerializedName("type") var type: String = "",
        @SerializedName("source_id") var sourceId: Int = 0,
        @SerializedName("date") var date: Long = 0,
        @SerializedName("post_id") var postId: Int = 0,
        @SerializedName("post_type") var postType: String = "",
        @SerializedName("text") var text: String = "",
        @SerializedName("marked_as_ads") var markedAsAds: Int = 0,
        @SerializedName("attachments") var attachments: RealmList<ApiAttachment>? = null,
        @SerializedName("post_source") var postSource: PostSource? = PostSource(),
        @SerializedName("comments") var comments: Comments? = Comments(),
        @SerializedName("likes") var likes: Likes? = Likes(),
        @SerializedName("reposts") var reposts: Reposts? = Reposts(),
        @SerializedName("views") var views: Views? = Views(),
//        @SerializedName("photos") var photos: Photos? = Photos(),
//        @SerializedName("note") var note: Note? = null,
        @SerializedName("signer_id") var signerId: Int = 0

) : RealmObject() {
    @SerializedName("copyHistory")
    private var copyHistory: RealmList<News>? = RealmList()

    lateinit var senderName: String

    lateinit var signerName: String

    var senderPhoto: String? = null

    var contentPhoto: String? = null
        get() {
            val attachment = attachments?.find {
                it.type == VKAttachments.TYPE_PHOTO &&
                        it.photo?.ownerId == sourceId
            }

            return if (attachment != null && attachment.photo?.photo807 != null) {
                attachment.photo?.photo807
            } else {
                attachment?.photo?.photo604
            }
        }

//    var contentVideo: String? = null

    fun haveSharedRepost(): Boolean? = (copyHistory != null && copyHistory?.size!! > 0)

    fun getSharedRepost(): News? {
        val haveSharedRepost = haveSharedRepost()

        return if (haveSharedRepost != null &&
                haveSharedRepost) copyHistory!![0] else null
    }


    internal enum class Type(val type: String) {
        PHOTOS("photos"),
        WALL_PHOTO("wall_photo"),
        POST("post")
    }
}


