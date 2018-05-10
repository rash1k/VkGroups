package ua.rash1k.vkgroups.ui.view.holder.attachment

import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.item_attachment_doc.view.*
import ua.rash1k.vkgroups.common.utils.openUrlInActionView
import ua.rash1k.vkgroups.models.view.attachment.DocAttachmentViewModel
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder


class DocAttachmentViewHolder(view: View) : BaseViewHolder<DocAttachmentViewModel>(view) {

    var title: TextView = view.tv_attachment_title

    var ext: TextView = view.tv_attachment_ext

    var size: TextView = view.tv_attachment_size


    override fun bindViewHolder(itemModel: DocAttachmentViewModel) {
        if (itemModel.needClick) {
            itemView.setOnClickListener { view -> openUrlInActionView(itemModel.mUrl, view.context) }
        }

        title.text = itemModel.mTitle

        size.text = itemModel.mSize

        ext.text = itemModel.mExt
    }

    override fun unbindViewHolder() {

        itemView.setOnClickListener(null)

        title.text = null

        size.text = null

        ext.text = null
    }
}