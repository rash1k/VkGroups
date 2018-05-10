package ua.rash1k.vkgroups.rest.model.response

import com.google.gson.annotations.SerializedName
import java.util.*

/*//Этот уровень содержит WallItem, count, profiles, groups
data class BaseItemResponse<out T>(val count: Int,
                                   val items: List<T> = ArrayList())*/

//Этот уровень содержит WallItem, count, profiles, groups
open class BaseItemResponse<T> {

    @SerializedName("count")
    var count: Int = 0
    @SerializedName("items")
    var items: List<T> = ArrayList()
}