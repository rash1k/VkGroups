package ua.rash1k.vkgroups.models.view.attachment

import android.view.View
import ua.rash1k.vkgroups.common.utils.formatSize
import ua.rash1k.vkgroups.common.utils.removeExtFromText
import ua.rash1k.vkgroups.models.attachment.doc.Doc
import ua.rash1k.vkgroups.models.view.BaseViewModel
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder
import ua.rash1k.vkgroups.ui.view.holder.attachment.DocImageAttachmentViewHolder


class DocImageAttachmentViewModel(doc: Doc) : BaseViewModel() {

    val mTitle: String = if (doc.title.isEmpty()) "Document" else removeExtFromText(doc.title)

    val mSize: String = formatSize(doc.size)

    val mExt: String = "." + doc.ext

    val mUrl: String = doc.url

    var mImageUrl: String = ""

    init {
        val sizes = doc.preview?.photo?.sizes
        if (sizes != null) {
            mImageUrl = sizes[sizes.size - 1]?.src ?: ""
        }
    }

    override fun getTypeIdLayout(): LayoutTypes {
        return LayoutTypes.AttachmentDocImage
    }

    override fun onCreateViewHolder(view: View): BaseViewHolder<BaseViewModel> {
        return DocImageAttachmentViewHolder(view) as BaseViewHolder<BaseViewModel>
    }
}