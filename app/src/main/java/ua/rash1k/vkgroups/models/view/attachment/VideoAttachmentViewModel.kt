package ua.rash1k.vkgroups.models.view.attachment

import android.view.View
import ua.rash1k.vkgroups.common.utils.formatViewsCount
import ua.rash1k.vkgroups.common.utils.getReadableDurationString
import ua.rash1k.vkgroups.models.attachment.video.Video
import ua.rash1k.vkgroups.models.view.BaseViewModel
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder
import ua.rash1k.vkgroups.ui.view.holder.attachment.VideoAttachmentViewHolder


class VideoAttachmentViewModel(video: Video) : BaseViewModel() {

    val id: Int = video.id

    val ownerId = video.ownerId

    val mVideoTitle: String = if (video.title == "") "Video" else video.title ?: "Video"

    val mViewCount: String = formatViewsCount(video.views)

    val mDuration: String = getReadableDurationString(video.duration)

    val mImageUrl: String = video.photo320 ?: ""

    override fun getTypeIdLayout(): LayoutTypes {
        return LayoutTypes.AttachmentVideo
    }

    override fun onCreateViewHolder(view: View): BaseViewHolder<BaseViewModel> {
        return VideoAttachmentViewHolder(view) as BaseViewHolder<BaseViewModel>
    }
}