package ua.rash1k.vkgroups.models.view.attachment

import android.view.View
import ua.rash1k.vkgroups.common.utils.formatSize
import ua.rash1k.vkgroups.common.utils.removeExtFromText
import ua.rash1k.vkgroups.models.attachment.doc.Doc
import ua.rash1k.vkgroups.models.view.BaseViewModel
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder
import ua.rash1k.vkgroups.ui.view.holder.attachment.DocAttachmentViewHolder
import java.io.File


class DocAttachmentViewModel : BaseViewModel {

    var mTitle: String

    var mSize: String

    var mExt: String

    lateinit var mUrl: String

    lateinit var mFile: File

    var needClick: Boolean = true

    constructor(doc: Doc) {
        mTitle = if (doc.title.isEmpty()) "Document" else removeExtFromText(doc.title)
        mSize = formatSize(doc.size)
        mExt = "." + doc.ext
        mUrl = doc.url
    }

    constructor(file: File) {
        val fileName = file.name
        val fileNameArray = fileName.split("""\.""")
        val extensions = fileNameArray[fileNameArray.size - 1]

        mFile = file
        mTitle = file.name
        mExt = ".$extensions"
        mSize = formatSize(file.length())

        needClick = false
    }


    override fun getTypeIdLayout(): LayoutTypes {
        return LayoutTypes.AttachmentDoc
    }

    override fun onCreateViewHolder(view: View): BaseViewHolder<BaseViewModel> {
        return DocAttachmentViewHolder(view) as BaseViewHolder<BaseViewModel>
    }
}