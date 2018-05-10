package ua.rash1k.vkgroups.models.view

import android.view.View
import ua.rash1k.vkgroups.models.WallItem
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder
import ua.rash1k.vkgroups.ui.view.holder.NewsItemHeaderHolder


class NewsItemHeaderViewModel(wallItem: WallItem) : BaseViewModel() {

    val mId: Int = wallItem.id
    val mProfilePhoto: String? = wallItem.senderPhoto
    val mProfileName: String = wallItem.senderName
    val mIsReposts: Boolean = wallItem.haveSharedRepost()
    var mRepostsProfileName: String? = null

    init {
        if (mIsReposts) {
            mRepostsProfileName = wallItem.getSharedRepost()?.senderName
        }
    }


    override fun getTypeIdLayout(): LayoutTypes {
        return LayoutTypes.NewsFeedHeader
    }

    override fun onCreateViewHolder(view: View): BaseViewHolder<BaseViewModel> {
        return NewsItemHeaderHolder(view) as BaseViewHolder<BaseViewModel>
    }
}