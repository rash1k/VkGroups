package ua.rash1k.vkgroups.models

import com.google.gson.annotations.SerializedName
import com.vk.sdk.api.model.Identifiable
import io.realm.RealmObject


open class Note : Identifiable, RealmObject() {
    @SerializedName("id")
    var _id = 0 //integer	идентификатор заметки.

    var owner_id = 0 //integer    идентификатор владельца заметки.
    var title = "" //string    заголовок заметки.
    var text = "" //string    текст заметки.
    var date: Long = 0 //integer    дата создания заметки в формате Unixtime.
    var comments: Int = 0 //integer    количество комментариев.
    var read_comments = 0 //integer    количество прочитанных комментариев (только при запросе информации о заметке текущего пользователя).
    var view_url = "" //string    URL страницы для отобр

    override fun getId() = _id

}
