package ua.rash1k.vkgroups.rest.model.request

import com.google.gson.annotations.SerializedName
import com.vk.sdk.api.VKApiConst
import ua.rash1k.vkgroups.consts.ApiConstants


//Model для запроса которая принимает значения
class WallGetRequestModel(@SerializedName(VKApiConst.OWNER_ID)
                          var ownerId: Int = ApiConstants.MY_GROUP_ID,
                          @SerializedName(VKApiConst.COUNT)
                          var count: Int = ApiConstants.DEFAULT_COUNT,
                          @SerializedName(VKApiConst.OFFSET)
                          var offset: Int = 0,
                          @SerializedName(VKApiConst.EXTENDED)
                          val extended: Int = 1) : BaseRequestModel() {


    override fun onMapCreate(map: HashMap<String, String>) {
        map[VKApiConst.OWNER_ID] = ownerId.toString()
        map[VKApiConst.COUNT] = count.toString()
        map[VKApiConst.OFFSET] = offset.toString()
        map[VKApiConst.EXTENDED] = extended.toString()
    }
}