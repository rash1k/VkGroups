package ua.rash1k.vkgroups.models.attachment

import com.vk.sdk.api.model.VKAttachments
import io.realm.RealmObject
import ua.rash1k.vkgroups.models.attachment.doc.Doc
import ua.rash1k.vkgroups.models.attachment.video.Video


//Служит контейнером для самих Attachment
 open class ApiAttachment : RealmObject() {

    lateinit var type: String

    var photo: Photo? = null
    var audio: Audio? = null
    var video: Video? = null
    var doc: Doc? = null
    var link: Link? = null
    var page: Page? = null

    //В зависимости от типа мы получаем объект со значениями
    fun getAttachment(): Attachment? =
            when (type) {
                VKAttachments.TYPE_PHOTO -> photo
                VKAttachments.TYPE_AUDIO -> audio
                VKAttachments.TYPE_DOC -> doc
                VKAttachments.TYPE_LINK -> link
                VKAttachments.TYPE_WIKI_PAGE -> page
                VKAttachments.TYPE_VIDEO -> video
                else -> throw NoSuchElementException("Attachment $type is not supported")
            }
}