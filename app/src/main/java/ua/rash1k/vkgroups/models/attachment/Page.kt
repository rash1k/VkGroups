package ua.rash1k.vkgroups.models.attachment

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject


open class Page : RealmObject(), Attachment {

    @SerializedName("id")
    @Expose
    internal var id: Int = 0

    @SerializedName("title")
    @Expose
    lateinit var title: String

    @SerializedName("view_url")
    @Expose
    lateinit var url: String

    override fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    override fun getType(): String {
        return "page"
    }
}