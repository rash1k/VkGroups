package ua.rash1k.vkgroups.ui.view.holder.attachment

import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.item_attachment_page.view.*
import ua.rash1k.vkgroups.common.utils.openUrlInActionView
import ua.rash1k.vkgroups.models.view.attachment.PageAttachmentViewModel
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder


class PageAttachmentViewHolder(view: View) : BaseViewHolder<PageAttachmentViewModel>(view) {

    private val tvTitle: TextView = view.tv_title


    override fun bindViewHolder(itemModel: PageAttachmentViewModel) {
        itemView.setOnClickListener { view -> openUrlInActionView(itemModel.mUrl, view.context) }

        tvTitle.text = itemModel.mTitle
    }

    override fun unbindViewHolder() {
        itemView.setOnClickListener(null)
        tvTitle.text = null
    }
}