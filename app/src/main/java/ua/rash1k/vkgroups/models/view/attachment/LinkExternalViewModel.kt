package ua.rash1k.vkgroups.models.view.attachment

import android.view.View
import ua.rash1k.vkgroups.models.attachment.Link
import ua.rash1k.vkgroups.models.view.BaseViewModel
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder
import ua.rash1k.vkgroups.ui.view.holder.attachment.LinkExternalViewHolder


class LinkExternalViewModel(link: Link) : BaseViewModel() {

    var linkTitle: String

    var url: String = link.url

    var linkImageUrl: String = link.photo?.photo604 ?: ""


    init {
        linkTitle = if (link.title.isEmpty()) {
            link.name ?: "Link"
        } else {
            link.title
        }
    }

    override fun getTypeIdLayout(): LayoutTypes {
        return LayoutTypes.AttachmentLinkExternal
    }

    override fun onCreateViewHolder(view: View): BaseViewHolder<BaseViewModel> {
        return LinkExternalViewHolder(view) as BaseViewHolder<BaseViewModel>
    }
}