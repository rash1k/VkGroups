package ua.rash1k.vkgroups.models.view.attachment

import android.view.View
import ua.rash1k.vkgroups.models.attachment.Photo
import ua.rash1k.vkgroups.models.view.BaseViewModel
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder
import ua.rash1k.vkgroups.ui.view.holder.attachment.ImageAttachmentHolder


class ImageAttachmentViewModel : BaseViewModel {

    var mPhotoUrl: String

    var needClick: Boolean = true

    constructor(url: String) {
        mPhotoUrl = url
        needClick = false
    }

    constructor(photo: Photo) {
        mPhotoUrl = photo.photo604 ?: "null"
    }


    override fun getTypeIdLayout(): LayoutTypes {
        return LayoutTypes.AttachmentImage
    }

    override fun onCreateViewHolder(view: View): BaseViewHolder<BaseViewModel> {
        return ImageAttachmentHolder(view) as BaseViewHolder<BaseViewModel>
    }
}