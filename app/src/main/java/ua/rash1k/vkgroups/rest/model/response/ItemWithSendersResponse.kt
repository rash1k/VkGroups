package ua.rash1k.vkgroups.rest.model.response

import com.google.gson.annotations.SerializedName
import ua.rash1k.vkgroups.models.Group
import ua.rash1k.vkgroups.models.Owner
import ua.rash1k.vkgroups.models.Profile


//Он нужен для того чтобы парсить не только items, но и такие поля как profiles и groups.
class ItemWithSendersResponse<T> : BaseItemResponse<T>() {

    @SerializedName("profiles")
    var profiles: List<Profile> = arrayListOf()
    @SerializedName("groups")
    var groups: List<Group> = arrayListOf()

    @SerializedName("new_offset")
    var newOffset: String? = null


    @SerializedName("next_from")
    var nextFrom: String? = null


    fun getSender(id: Int): Owner? {
        //Old
        /* for (owner in getAllSenders()) {
             if (owner.id == Math.abs(id)) return owner
         }
         return null*/

        return getAllSenders().find { it.id == Math.abs(id) }
    }


    private fun getAllSenders(): List<Owner> {
        val all = arrayListOf<Owner>()
        all.addAll(profiles)
        all.addAll(groups)
        return all
    }
}