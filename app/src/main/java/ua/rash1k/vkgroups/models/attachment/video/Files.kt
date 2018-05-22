package ua.rash1k.vkgroups.models.attachment.video

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject


open class Files : RealmObject() {

    @SerializedName("external")
    var linkToFile: String = ""
}