package ua.rash1k.vkgroups.models.view

import android.view.View
import android.widget.TextView

import com.bumptech.glide.Glide

import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_comment_header.view.*
import ua.rash1k.vkgroups.models.CommentItem
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder

class CommentHeaderViewModel(commentItem: CommentItem) : BaseViewModel() {

    val id: Int = commentItem.id

    val profilePhoto: String = commentItem.senderPhoto

    val profileName: String = commentItem.senderName


    override fun getTypeIdLayout(): LayoutTypes {
        return LayoutTypes.CommentHeader
    }

    override fun onCreateViewHolder(view: View): BaseViewHolder<BaseViewModel> {
        return CommentHeaderHolder(view) as BaseViewHolder<BaseViewModel>
    }

    class CommentHeaderHolder(itemView: View) : BaseViewHolder<CommentHeaderViewModel>(itemView) {

        var civProfileImage: CircleImageView = itemView.civ_profile_image

        var tvName: TextView = itemView.tv_profile_name


        override fun bindViewHolder(itemModel: CommentHeaderViewModel) {
            Glide.with(itemView.context).load(itemModel.profilePhoto).into(civProfileImage)

            tvName.text = itemModel.profileName
        }

        override fun unbindViewHolder() {
            civProfileImage.setImageBitmap(null)
            tvName.text = null
        }
    }


}