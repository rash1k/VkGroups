package ua.rash1k.vkgroups.models.view

import android.view.View
import ua.rash1k.vkgroups.models.WallItem
import ua.rash1k.vkgroups.models.view.counter.CommentCounterViewModel
import ua.rash1k.vkgroups.models.view.counter.LikeCounterViewModel
import ua.rash1k.vkgroups.models.view.counter.RepostCounterViewModel
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder
import ua.rash1k.vkgroups.ui.view.holder.NewsItemFooterHolder


class NewsItemFooterViewModel(wallItem: WallItem) : BaseViewModel() {

    val mId = wallItem.id
    val mOwnerId = wallItem.ownerId
    val mCommentsCounterViewModel: CommentCounterViewModel = CommentCounterViewModel(wallItem.comments!!)
    val mRepostCounterViewModel: RepostCounterViewModel = RepostCounterViewModel(wallItem.reposts!!)
    var mLikeCounterViewModel: LikeCounterViewModel = LikeCounterViewModel(wallItem.likes!!)
    val mDataLong = wallItem.date

    override fun getTypeIdLayout(): LayoutTypes {
        return LayoutTypes.NewsFeedFooter
    }

    override fun onCreateViewHolder(view: View): BaseViewHolder<BaseViewModel> {
        return NewsItemFooterHolder(view) as BaseViewHolder<BaseViewModel>
    }

    override fun isItemDecoration(): Boolean {
        return true
    }
}