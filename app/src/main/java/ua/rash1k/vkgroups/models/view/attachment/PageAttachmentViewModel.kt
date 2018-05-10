package ua.rash1k.vkgroups.models.view.attachment

import android.view.View
import ua.rash1k.vkgroups.models.attachment.Page
import ua.rash1k.vkgroups.models.view.BaseViewModel
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder
import ua.rash1k.vkgroups.ui.view.holder.attachment.PageAttachmentViewHolder


class PageAttachmentViewModel(page: Page) : BaseViewModel() {

    var mTitle: String = page.title
    var mUrl = page.url


    override fun getTypeIdLayout(): LayoutTypes {
        return LayoutTypes.AttachmentPage
    }

    override fun onCreateViewHolder(view: View): BaseViewHolder<BaseViewModel> {
        return PageAttachmentViewHolder(view) as BaseViewHolder<BaseViewModel>
    }
}