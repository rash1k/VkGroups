package ua.rash1k.vkgroups.ui.view.holder

import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.item_news_footer.view.*
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.common.manager.MyFragmentManager
import ua.rash1k.vkgroups.common.utils.formatDate
import ua.rash1k.vkgroups.models.Place
import ua.rash1k.vkgroups.models.view.NewsItemFooterViewModel
import ua.rash1k.vkgroups.models.view.counter.CounterViewModel
import ua.rash1k.vkgroups.models.view.counter.LikeCounterViewModel
import ua.rash1k.vkgroups.ui.activity.BaseActivity
import ua.rash1k.vkgroups.ui.fragment.CommentsFragment
import javax.inject.Inject

class NewsItemFooterHolder(view: View) : BaseViewHolder<NewsItemFooterViewModel>(view) {

    private val tvDate = view.tv_date
    private val tvLikesCount = view.tv_likes_count
    private val tvLikesIcon = view.tv_likes_icon
    private val tvRepostCount = view.tv_reposts_count
    private val tvRepostIcon = view.tv_reposts_icon
    private val tvCommentsCount = view.tv_comments_count
    private val tvCommentsIcon = view.tv_comments_icon

    private val rlComments:View = view.rl_comments

    private var context: Context = view.context
    private var resourses: Resources?

    @Inject
    lateinit var mFontGoogle: Typeface

    @Inject
    lateinit var myFragmentManager: MyFragmentManager

    init {
        MyApplication.sApplicationComponent.inject(this)
        resourses = context.resources

        tvCommentsIcon.typeface = mFontGoogle
        tvRepostIcon.typeface = mFontGoogle
        tvLikesIcon.typeface = mFontGoogle
    }

    override fun bindViewHolder(itemModel: NewsItemFooterViewModel) {
        tvDate.text = formatDate(itemModel.mDataLong, context)

//        bindLikes(itemModel.mLikeCounterViewModel)
        bindData(tvLikesCount, tvLikesIcon, itemModel.mLikeCounterViewModel)
        bindData(tvCommentsCount, tvCommentsIcon, itemModel.mCommentsCounterViewModel)
        bindData(tvRepostCount, tvRepostIcon, itemModel.mRepostCounterViewModel)


        rlComments.setOnClickListener {
            myFragmentManager.addFragment(
                    it.context as BaseActivity?,
                    CommentsFragment.newInstance(
                            Place(itemModel.mOwnerId.toString(), itemModel.mId.toString())),
                    R.id.main_wrapper)
        }

        tvLikesIcon.setOnClickListener { }
    }

    private fun bindLikes(likes: LikeCounterViewModel) {
        tvLikesCount.text = likes.mCount.toString()
        tvLikesCount.setTextColor(likes.mIconColor)
        tvLikesIcon.setTextColor(likes.mIconColor)
    }


    private fun bindComments(likes: LikeCounterViewModel) {
        tvCommentsCount.text = likes.mCount.toString()
        tvLikesCount.setTextColor(likes.mIconColor)
        tvLikesIcon.setTextColor(likes.mIconColor)
    }

    private fun <T : CounterViewModel> bindData(tvCount: TextView,
                                                tvIcon: TextView, item: T) {
        tvCount.text = item.mCount.toString()
        resourses?.getColor(item.mTextColor)?.let { tvCount.setTextColor(it) }
        resourses?.getColor(item.mTextColor)?.let(tvIcon::setTextColor)
    }

    override fun unbindViewHolder() {
        tvDate.text = null
        tvLikesCount.text = null
        tvRepostCount.text = null
        tvCommentsCount.text = null
    }
}
