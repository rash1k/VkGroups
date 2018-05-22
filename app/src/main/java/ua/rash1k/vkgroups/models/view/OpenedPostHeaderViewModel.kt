package ua.rash1k.vkgroups.models.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_opened_post_header.view.*
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.common.utils.UiHelper
import ua.rash1k.vkgroups.models.CommentItem
import ua.rash1k.vkgroups.models.WallItem
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder
import javax.inject.Inject


class OpenedPostHeaderViewModel : BaseViewModel {

    var mId: Int

    var mProfileName: String

    var mProfilePhoto: String

    var mText: String


    constructor(wallItem: WallItem) {
        mId = wallItem.id

        mProfileName = wallItem.senderName

        mProfilePhoto = wallItem.senderPhoto!!

        mText = wallItem.text

    }

    constructor(commentItem: CommentItem) {
        mId = commentItem.id

        mProfileName = commentItem.senderName

        mProfilePhoto = commentItem.senderPhoto

        mText = commentItem.text

    }

    override fun getTypeIdLayout(): LayoutTypes {
        return LayoutTypes.OpenedPostHeader
    }

    override fun onCreateViewHolder(view: View): BaseViewHolder<BaseViewModel> {
        return OpenedPostHeaderHolder(view) as BaseViewHolder<BaseViewModel>
    }


    class OpenedPostHeaderHolder(view: View) : BaseViewHolder<OpenedPostHeaderViewModel>(view) {

        @Inject
        lateinit var uiHelper: UiHelper


        val ivProfileImage: ImageView = view.civ_profile_image
        val tvProfileName: TextView = view.tv_profile_name
        val tvText: TextView = view.tv_text

        init {
            MyApplication.sApplicationComponent.inject(this)
        }

        override fun bindViewHolder(itemModel: OpenedPostHeaderViewModel) {
            Glide.with(itemView.context).load(itemModel.mProfilePhoto).into(ivProfileImage)

            tvProfileName.text = itemModel.mProfileName

            uiHelper.setUpTextViewWithVisibility(tvText, itemModel.mText)
//            tvText.text = itemModel.mText
        }

        override fun unbindViewHolder() {
            ivProfileImage.setImageBitmap(null)
            tvProfileName.text = null
            tvText.text = null
        }
    }
}