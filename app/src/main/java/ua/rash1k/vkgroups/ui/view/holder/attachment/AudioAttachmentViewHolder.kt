package ua.rash1k.vkgroups.ui.view.holder.attachment

import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.item_attachment_audio.view.*
import ua.rash1k.vkgroups.models.view.attachment.AudioAttachmentViewModel
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder


class AudioAttachmentViewHolder(view: View) : BaseViewHolder<AudioAttachmentViewModel>(view) {

    private val mTitle: TextView = view.tv_audio_name

    private val mArtist: TextView = view.tv_audio_artist

    private val mDuration = view.tv_audio_duration


    override fun bindViewHolder(itemModel: AudioAttachmentViewModel) {
        mTitle.text = itemModel.mTitle
        mArtist.text = itemModel.mArtist
        mDuration.text = itemModel.mDuration
    }

    override fun unbindViewHolder() {
        mTitle.text = null
        mArtist.text = null
        mDuration.text = null
    }
}