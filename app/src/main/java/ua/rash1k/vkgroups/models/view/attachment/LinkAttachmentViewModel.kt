package ua.rash1k.vkgroups.models.view.attachment

import android.view.View
import ua.rash1k.vkgroups.models.attachment.Link
import ua.rash1k.vkgroups.models.view.BaseViewModel
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder
import ua.rash1k.vkgroups.ui.view.holder.attachment.LinkAttachmentViewHolder


class LinkAttachmentViewModel(link: Link) : BaseViewModel() {


    override fun getTypeIdLayout(): LayoutTypes {
        return LayoutTypes.AttachmentLink
    }


    var mTitle: String = if (link.title.isEmpty()) link.name ?: "Link" else link.title
    var mUrl: String = link.url


    override fun onCreateViewHolder(view: View): BaseViewHolder<BaseViewModel> {
        return LinkAttachmentViewHolder(view) as BaseViewHolder<BaseViewModel>
    }
}