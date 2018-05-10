package ua.rash1k.vkgroups.rest.model.request

import com.google.gson.annotations.SerializedName
import com.vk.sdk.api.VKApiConst
import ua.rash1k.vkgroups.consts.ApiConstants


class NewsFeedRequestModel(
        @SerializedName(VKApiConst.FILTERS)
        val filters: String = ApiConstants.NEWS_FILTERS,
        @SerializedName("return_banned")
        val returnBanned: Int = 1,
        @SerializedName("start_from")
        var start_from: Int = 0,
        @SerializedName("count")
        var count: Int = ApiConstants.DEFAULT_COUNT) : BaseRequestModel() {


    override fun onMapCreate(map: HashMap<String, String>) {
        map[VKApiConst.FILTERS] = filters
        map["return_banned"] = returnBanned.toString()
        map["start_from"] = start_from.toString()
        map[VKApiConst.COUNT] = count.toString()
    }
}