package ua.rash1k.vkgroups.models.attachment.doc

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject


 open class SizePhoto : RealmObject() {

    @SerializedName("src")
    var src: String = ""

    @SerializedName("width")
    var width: String = ""

    @SerializedName("height")
    var height: String = ""

    @SerializedName("type")
    var type: String = ""
}