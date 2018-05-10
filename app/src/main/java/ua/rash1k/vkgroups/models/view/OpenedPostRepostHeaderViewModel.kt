package ua.rash1k.vkgroups.models.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_opened_post_repost_header.view.*
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.common.utils.UiHelper
import ua.rash1k.vkgroups.common.utils.formatDate
import ua.rash1k.vkgroups.models.WallItem
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder
import javax.inject.Inject


class OpenedPostRepostHeaderViewModel(forwardedPost: WallItem) : BaseViewModel() {


    val mId = forwardedPost.id

    val mProfileName = forwardedPost.senderName

    val mProfilePhoto = forwardedPost.senderPhoto

    val mText = forwardedPost.text

    val mDate: Long = forwardedPost.date


    override fun getTypeIdLayout(): LayoutTypes {
        return LayoutTypes.OpenedPostRepostHeader
    }

    override fun onCreateViewHolder(view: View): BaseViewHolder<BaseViewModel> {
        return OpenedPostRepostViewHolder(view) as BaseViewHolder<BaseViewModel>
    }


    class OpenedPostRepostViewHolder(view: View) : BaseViewHolder<OpenedPostRepostHeaderViewModel>(view) {


        @Inject
        lateinit var uiHelper: UiHelper


        val ivRepostProfileImage: ImageView = view.civ_repost_profile_image
        val tvRepostProfileName: TextView = view.tv_repost_profile_name
        val tvRepostDate: TextView = view.tv_repost_date
        val tvRepostText: TextView = view.tv_text

        init {
            MyApplication.sApplicationComponent.inject(this)
        }

        override fun bindViewHolder(itemModel: OpenedPostRepostHeaderViewModel) {
            Glide.with(itemView.context).load(itemModel.mProfilePhoto).into(ivRepostProfileImage)

            tvRepostProfileName.text = itemModel.mProfileName

            uiHelper.setUpTextViewWithVisibility(tvRepostText, itemModel.mText)

            tvRepostDate.text = formatDate(itemModel.mDate,itemView.context)
        }

        override fun unbindViewHolder() {
            ivRepostProfileImage.setImageBitmap(null)
            tvRepostProfileName.text = null
            tvRepostText.text = null
            tvRepostDate.text = null
        }
    }
}