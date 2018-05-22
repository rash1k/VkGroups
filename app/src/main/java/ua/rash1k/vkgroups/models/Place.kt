package ua.rash1k.vkgroups.models

import android.os.Bundle
import com.vk.sdk.api.VKApiConst
import io.realm.RealmObject
import ua.rash1k.vkgroups.common.utils.splitStringForIdAndOwnerId


open class Place(var ownerId: String = "",
                 var postId: String = "") : RealmObject() {


    companion object {
        val PLACE = "place"
    }

    constructor(source: String) : this() {
        val placeId: List<String> = splitStringForIdAndOwnerId(source)
        ownerId = placeId[0]
        postId = placeId[1]
    }


    constructor(bundle: Bundle) : this() {
        ownerId = bundle.getString(VKApiConst.OWNER_ID)
        postId = bundle.getString(VKApiConst.POST_ID)
    }

    constructor(map: Map<String, String>) : this() {
        ownerId = map[VKApiConst.OWNER_ID] ?: ""
        postId = map[VKApiConst.POST_ID] ?: ""
    }


    fun toBundle(): Bundle {
        val bundle = Bundle()
        bundle.putString(VKApiConst.OWNER_ID, ownerId)
        bundle.putString(VKApiConst.POST_ID, postId)
        return bundle
    }

    override fun equals(o: Any?): Boolean {
        if (o === this) return true
        if (o !is Place) return false
        val other = o as Place?
        return other!!.ownerId == ownerId && other.postId == postId
    }

    override fun hashCode(): Int {
        var result = ownerId.hashCode()
        result = 31 * result + postId.hashCode()
        return result
    }
}