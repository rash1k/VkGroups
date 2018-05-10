package ua.rash1k.vkgroups.ui.view.holder.attachment

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_attachment_video.view.*
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.common.utils.openUrlInActionView
import ua.rash1k.vkgroups.models.view.attachment.VideoAttachmentViewModel
import ua.rash1k.vkgroups.rest.api.VideoApi
import ua.rash1k.vkgroups.rest.model.request.VideoGetRequestModel
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder
import javax.inject.Inject

class VideoAttachmentViewHolder(view: View) : BaseViewHolder<VideoAttachmentViewModel>(view) {

    val mVideoTitle = view.tv_attachment_video_title

    val mVideoImage: ImageView = view.iv_attachment_video_picture

    val mViewsCount: TextView = view.tv_views_count

    val mDuration = view.tv_attachment_video_duration

    @Inject
    lateinit var mVideoApi: VideoApi

    init {
        MyApplication.sApplicationComponent.inject(this)
    }

    override fun bindViewHolder(itemModel: VideoAttachmentViewModel) {
        itemView.setOnClickListener {
            mVideoApi.get(VideoGetRequestModel(itemModel.ownerId, itemModel.id).toMap())
                    .flatMap { full -> Observable.fromIterable(full.response?.items) }
                    .observeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe { video ->

                        val url: String = video?.files?.linkToFile ?: video.player ?: ""

                        openUrlInActionView(url, itemView.context)
                    }

        }

        mVideoTitle.text = itemModel.mVideoTitle

        Glide.with(itemView.context)
                .load(itemModel.mImageUrl)
                .into(mVideoImage)

        mViewsCount.text = itemView.context.getString(R.string.tv_views_count, itemModel.mViewCount)

        mDuration.text = itemModel.mDuration
    }

    override fun unbindViewHolder() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
