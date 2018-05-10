package ua.rash1k.vkgroups.rest.model.response

import com.google.gson.annotations.SerializedName


//Корневой ответ
open class Full<T> {

    @SerializedName("response")
    var response: T? = null

}


