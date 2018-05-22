package ua.rash1k.vkgroups.models

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import ua.rash1k.vkgroups.models.attachment.ApiAttachment
import ua.rash1k.vkgroups.models.attachment.Link
import ua.rash1k.vkgroups.models.countable.Comments
import ua.rash1k.vkgroups.models.countable.Likes
import ua.rash1k.vkgroups.models.countable.Reposts

open class Group : RealmObject(), Owner {

    internal var subscribeCount = 0

    @SerializedName("id")
    @PrimaryKey
    internal var id: Int = 0//86529522
    @SerializedName("name")
    var name: String = "" //VK Fest
    @SerializedName("screen_name")
    var screenName: String = "" //fest
    @SerializedName("is_closed")
    var isClosed: Int = 0//0
    @SerializedName("type")
    var type: String = "" //event
    @SerializedName("is_admin")
    var isAdmin: Int = 0 //0
    @SerializedName("is_member")
    var isMember: Int = 0//0
    @SerializedName("photo_50")
    var photo50: String = "" //https://pp.userap...6d0/VogboWaykYA.jpg
    @SerializedName("photo_100")
    var photo100: String = ""//https://pp.userap...6cf/B7yTl3PtpoE.jpg
    @SerializedName("photo_200")
    var photo200: String = "" //https://pp.userap...6ce/k4CKjWl2znY.jpg


    @SerializedName("status")
    var status: String = ""

    @SerializedName("description")
    var description: String = ""

    @SerializedName("site")
    var site: String = ""

    @SerializedName("links")
    var links: RealmList<Link> = RealmList()

    @SerializedName("contacts")
    var contactList: RealmList<Contact> = RealmList()


    override fun getFullName(): String {
        return name
    }

    override fun getPhoto(): String? {
        return photo100
    }

    override fun getId(): Int {
        return id
    }
}

open class Profile : RealmObject(), Owner {

    @SerializedName("id")
    @PrimaryKey //Позволит обновлять поля пользователя с таким же id
    internal var id: Int = 0
    @SerializedName("photo_50")
    var photo50: String? = null
    @SerializedName("photo_100")
    var photo100: String? = null
    @SerializedName("first_name")
    lateinit var firstName: String
    @SerializedName("last_name")
    lateinit var lastName: String
    @SerializedName("sex")
    var sex: Int = 0
    @SerializedName("screen_name")
    var screenName: String? = null
    @SerializedName("online")
    var online: Int = 0
    @SerializedName("hidden")
    var hidden: Int = 0


    var isContact: Boolean = false

    var isGroup: Boolean = false

    override fun getFullName(): String {
        return "$firstName $lastName"
    }

    override fun getPhoto(): String? {
        return photo100
    }


    override fun getId(): Int {
        return id
    }

    fun getDisplayProfilePhoto(): String? {
        return photo100
    }
}

open class WallItem : RealmObject() {

    var attachmentsString: String = ""
    var senderName: String = ""
    var senderPhoto: String? = null

    @PrimaryKey//Есть возвомжность обновлять элементы в Realm
    @SerializedName("id")
    var id: Int = 0 //126730
    @SerializedName("from_id")
    var fromId: Int = 0 //-86529522
    @SerializedName("owner_id")
    var ownerId: Int = 0 //-86529522
    @SerializedName("date")
    var date: Long = 0 //1489599074
    @SerializedName("marked_as_ads")
    var markedAsAds: Int = 0 //0
    @SerializedName("post_type")
    lateinit var postType: String //post
    @SerializedName("text")
    lateinit var text: String //Зона «Экстрим» на VK Fest 2017
    @SerializedName("can_pin")
    var canPin: Int = 0 //1
    @SerializedName("attachments")
    var attachments: RealmList<ApiAttachment> = RealmList()//Меняем тип с List на RealmList так-как Realm работает только со своим списком
    @SerializedName("copyHistory")
    private var copyHistory: RealmList<WallItem> = RealmList()
    @SerializedName("post_source")
    var postSource: PostSource? = null
    @SerializedName("comments")

    var comments: Comments? = null
    @SerializedName("likes")
    var likes: Likes? = null
    @SerializedName("reposts")
    var reposts: Reposts? = null
    @SerializedName("views")
    var views: Views? = null


    fun haveSharedRepost(): Boolean = (copyHistory.size > 0)

    fun getSharedRepost(): WallItem? {
        val haveSharedRepost = haveSharedRepost()

        return if (haveSharedRepost) copyHistory[0] else null
    }
}


open class PostSource : RealmObject() {
    @SerializedName("type")
    lateinit var type: String
}

open class Views : RealmObject() {
    @SerializedName("count")
    var count: Int = 0 //27807
}




