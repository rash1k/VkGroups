package ua.rash1k.vkgroups.ui.view.holder.attachment

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_attachment_doc_image.view.*
import ua.rash1k.vkgroups.common.utils.openUrlInActionView
import ua.rash1k.vkgroups.models.view.attachment.DocImageAttachmentViewModel
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder


class DocImageAttachmentViewHolder(view: View) : BaseViewHolder<DocImageAttachmentViewModel>(view) {

    val title: TextView = view.tv_attachment_title

    val image: ImageView = view.iv_attachment_image

    val size: TextView = view.tv_attachment_size

    val ext: TextView = view.tv_attachment_ext


    override fun bindViewHolder(itemModel: DocImageAttachmentViewModel) {
        openUrlInActionView(itemModel.mUrl, itemView.context)

        Glide.with(itemView.context).load(itemModel.mUrl).into(image)

        title.text = itemModel.mTitle

        size.text = itemModel.mSize

        ext.text = itemModel.mExt
    }

    override fun unbindViewHolder() {
        itemView.setOnClickListener(null);
        title.text = null
        size.text = null
        ext.text = null
        image.setImageBitmap(null)
    }
}