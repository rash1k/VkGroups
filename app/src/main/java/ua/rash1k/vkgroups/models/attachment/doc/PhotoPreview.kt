package ua.rash1k.vkgroups.models.attachment.doc

import io.realm.RealmList
import io.realm.RealmObject


open class PhotoPreview : RealmObject() {

    var sizes: RealmList<SizePhoto> = RealmList()
}