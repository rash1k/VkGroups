package ua.rash1k.vkgroups.models.news

import com.google.gson.annotations.SerializedName
import com.vk.sdk.api.VKApiConst
import io.realm.RealmList
import io.realm.RealmObject
import ua.rash1k.vkgroups.models.attachment.Photo


open class Photos : RealmObject(), ApiType {
    @SerializedName("count")
    var count: Int = 0

    @SerializedName("items")
    var items: RealmList<Photo> = RealmList()

    override fun getType(): String {

        return VKApiConst.PHOTOS
    }

    fun getPhoto(sourceId: Int): String {
        val single = items.single { photo -> photo.ownerId == sourceId }
        return single.photo604 ?: ""
    }
}
