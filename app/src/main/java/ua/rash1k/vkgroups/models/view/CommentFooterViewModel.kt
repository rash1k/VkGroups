package ua.rash1k.vkgroups.models.view

import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.item_comment_footer.view.*
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.common.utils.formatDate
import ua.rash1k.vkgroups.models.CommentItem
import ua.rash1k.vkgroups.models.WallItem
import ua.rash1k.vkgroups.models.view.counter.LikeCounterViewModel
import ua.rash1k.vkgroups.mvp.view.PostFooterView
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder
import javax.inject.Inject

class CommentFooterViewModel(commentItem: CommentItem) : BaseViewModel() {


    val id: Int = commentItem.id

    val dateLong: Long = commentItem.date.toLong()

    val likes: LikeCounterViewModel = LikeCounterViewModel(commentItem.likes)

    override fun isItemDecoration(): Boolean {
        return true
    }

    override fun getTypeIdLayout(): LayoutTypes {
        return LayoutTypes.CommentFooter

    }


    init {
//        likes = LikeCounterViewModel(commentItem.likes)
    }

    override fun onCreateViewHolder(view: View): BaseViewHolder<BaseViewModel> {
        return CommentFooterHolder(view) as BaseViewHolder<BaseViewModel>
    }


    class CommentFooterHolder(itemView: View) : BaseViewHolder<CommentFooterViewModel>(itemView), PostFooterView {

        var tvDate: TextView = itemView.tv_date

        var rlLikes: View = itemView.rl_likes

        var tvLikesCount: TextView = itemView.tv_likes_count

        var tvLikesIcon: TextView = itemView.tv_likes_icon

        @Inject
        lateinit var mGoogleFontTypeface: Typeface

        private val mResources: Resources

        private val mContext: Context


        init {
            MyApplication.sApplicationComponent.inject(this)

            mContext = itemView.context
            mResources = mContext.resources

            tvLikesIcon.typeface = mGoogleFontTypeface
        }

        override fun bindViewHolder(itemModel: CommentFooterViewModel) {

            tvDate.text = formatDate(itemModel.dateLong, mContext)

            bindLikes(itemModel.likes)
        }

        private fun bindLikes(likes: LikeCounterViewModel) {
            tvLikesCount.text = likes.mCount.toString()
            tvLikesCount.setTextColor(mResources.getColor(likes.mTextColor))
            tvLikesIcon.setTextColor(mResources.getColor(likes.mIconColor))
        }


        override fun unbindViewHolder() {
            rlLikes.setOnClickListener(null)

            tvDate.text = null

            tvLikesCount.text = null
        }


        override fun like(likes: LikeCounterViewModel) {

        }

        override fun openComments(wallItem: WallItem) {

        }
    }
}