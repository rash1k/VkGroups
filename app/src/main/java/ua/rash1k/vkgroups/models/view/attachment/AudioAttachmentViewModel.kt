package ua.rash1k.vkgroups.models.view.attachment

import android.view.View
import ua.rash1k.vkgroups.common.utils.getReadableDurationString
import ua.rash1k.vkgroups.models.attachment.Audio
import ua.rash1k.vkgroups.models.view.BaseViewModel
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder
import ua.rash1k.vkgroups.ui.view.holder.attachment.AudioAttachmentViewHolder


class AudioAttachmentViewModel(audio: Audio) : BaseViewModel() {

    var mTitle: String = if (audio.title == "") "Title" else audio.title

    var mArtist: String = if (audio.artist == "") "Various Artist" else audio.artist

    var mDuration: String = getReadableDurationString(audio.duration)

    override fun getTypeIdLayout(): LayoutTypes {
        return LayoutTypes.AttachmentAudio
    }

    override fun onCreateViewHolder(view: View): BaseViewHolder<BaseViewModel> {
        return AudioAttachmentViewHolder(view) as BaseViewHolder<BaseViewModel>
    }
}