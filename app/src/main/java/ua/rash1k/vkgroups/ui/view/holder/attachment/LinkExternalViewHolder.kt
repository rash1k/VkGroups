package ua.rash1k.vkgroups.ui.view.holder.attachment

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_attachment_link_external.view.*
import ua.rash1k.vkgroups.common.utils.openUrlInActionView
import ua.rash1k.vkgroups.models.view.attachment.LinkExternalViewModel
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder


class LinkExternalViewHolder(view: View) : BaseViewHolder<LinkExternalViewModel>(view) {

    var title: TextView = view.tv_title

    var image: ImageView = view.iv_attachment_picture

    var url: TextView = view.tv_attachment_url


    override fun bindViewHolder(itemModel: LinkExternalViewModel) {
        itemView.setOnClickListener { view -> openUrlInActionView(itemModel.url, view.context) }
        title.text = itemModel.linkTitle
        url.text = itemModel.url

        Glide.with(itemView.context).load(itemModel.url).into(image)
    }

    override fun unbindViewHolder() {
        itemView.setOnClickListener(null)
        title.text = null
        title.text = null

        image.setImageBitmap(null)
    }
}