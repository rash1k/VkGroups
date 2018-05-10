package ua.rash1k.vkgroups.models.view

import android.view.View
import ua.rash1k.vkgroups.models.WallItem
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder
import ua.rash1k.vkgroups.ui.view.holder.NewsItemBodyHolder


class NewsItemBodyViewModel(wallItem: WallItem) : BaseViewModel() {

    val id: Int = wallItem.id
    var text: String = wallItem.text


    lateinit var mAttachmentString: String

    init {
        val mIsRepost: Boolean = wallItem.haveSharedRepost()
        if (mIsRepost) {
            text = wallItem.getSharedRepost()!!.text
            val sharedRepost = wallItem.getSharedRepost()
            if (sharedRepost != null) {
                this.mAttachmentString = sharedRepost.attachmentsString
            }
        } else {
            text = wallItem.text
            mAttachmentString = wallItem.attachmentsString
        }
    }

    override fun getTypeIdLayout(): LayoutTypes {
        return LayoutTypes.NewsFeedBody
    }


    override fun onCreateViewHolder(view: View): BaseViewHolder<BaseViewModel> {
        return NewsItemBodyHolder(view) as BaseViewHolder<BaseViewModel>
    }

    override fun isItemDecoration(): Boolean {
        return true
    }
}