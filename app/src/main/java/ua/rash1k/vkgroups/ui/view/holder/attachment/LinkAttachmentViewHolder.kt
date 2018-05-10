package ua.rash1k.vkgroups.ui.view.holder.attachment

import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.item_attachment_link.view.*
import ua.rash1k.vkgroups.common.utils.openUrlInActionView
import ua.rash1k.vkgroups.models.view.attachment.LinkAttachmentViewModel
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder


class LinkAttachmentViewHolder(view: View) : BaseViewHolder<LinkAttachmentViewModel>(view) {

    val title: TextView = view.tv_title

    val url: TextView = view.tv_attachment_url


    override fun bindViewHolder(itemModel: LinkAttachmentViewModel) {
        itemView.setOnClickListener { openUrlInActionView(itemModel.mUrl, it.context) }

        title.text = itemModel.mTitle
        url.text = itemModel.mUrl
    }

    override fun unbindViewHolder() {
        itemView.setOnClickListener(null)
        title.text = null
        url.text = null
    }
}