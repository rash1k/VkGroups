package ua.rash1k.vkgroups.models.view

import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_news_feed_body.view.*
import kotlinx.android.synthetic.main.item_news_feed_header.view.*
import kotlinx.android.synthetic.main.item_news_footer.view.*
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.common.utils.getFriendlyDateString
import ua.rash1k.vkgroups.models.News
import ua.rash1k.vkgroups.models.view.counter.CommentCounterViewModel
import ua.rash1k.vkgroups.models.view.counter.CounterViewModel
import ua.rash1k.vkgroups.models.view.counter.LikeCounterViewModel
import ua.rash1k.vkgroups.models.view.counter.RepostCounterViewModel
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder
import javax.inject.Inject


class NewsFeedViewModel(newsItem: News) : BaseViewModel() {

    val TAG = "NewsFeedViewModel"

    //Header
    val mId: Int = newsItem.id
    val mProfilePhoto: String? = newsItem.senderPhoto
    val mProfileName: String = newsItem.senderName
    val mIsReposts: Boolean? = newsItem.haveSharedRepost()
    var mRepostsProfileName: String? = null
    val mHeaderDate: Long = newsItem.date

    init {
        if (mIsReposts != null && mIsReposts) {
            mRepostsProfileName = newsItem.getSharedRepost()?.senderName
        }
    }

    //Body
    var textViewTextBody: String = newsItem.text
    val mImageViewBody: String = newsItem.contentPhoto ?: ""
    val mSignerName: String = newsItem.signerName


    //Footer
    val mCommentsCounterViewModel: CommentCounterViewModel = CommentCounterViewModel(newsItem.comments!!)
    val mRepostCounterViewModel: RepostCounterViewModel = RepostCounterViewModel(newsItem.reposts!!)
    val mLikeCounterViewModel: LikeCounterViewModel = LikeCounterViewModel(newsItem.likes!!)
    val mDateFooter = newsItem.date

    override fun getTypeIdLayout(): LayoutTypes {
        return LayoutTypes.NewsFeed
    }

    override fun onCreateViewHolder(view: View): BaseViewHolder<BaseViewModel> {
        return NewsFeedViewHolder(view) as BaseViewHolder<BaseViewModel>
    }


    class NewsFeedViewHolder(itemView: View) : BaseViewHolder<NewsFeedViewModel>(itemView) {
        val TAG = "NewsFeedViewHolder"


        @Inject
        lateinit var mFontGoogle: Typeface

        //Header
        private val civProfileImage: CircleImageView = itemView.civ_profile_image
        private val tvName: TextView = itemView.tv_profile_name
        private val ivRepostedIcon: ImageView = itemView.iv_reposted_icon
        private val tvRepostedProfileName: TextView = itemView.tv_reposted_profile_name
        private val tvHeaderDate: TextView = itemView.textViewDate


        //Body
        private val textViewBody: TextView = itemView.textViewTextBody
        private val imageViewBody: ImageView = itemView.imageViewBody
        private val imageSignedIcon: TextView = itemView.tv_signed_icon
        private val imageSignerName: TextView = itemView.textViewFullNameSigner

        init {
            MyApplication.sApplicationComponent.inject(this)
            imageSignedIcon.typeface = mFontGoogle
        }

        //Footer

        private val mTvFooterDate = itemView.tv_date
        private val tvLikesCount = itemView.tv_likes_count
        private val tvLikesIcon = itemView.tv_likes_icon
        private val tvRepostCount = itemView.tv_reposts_count
        private val tvRepostIcon = itemView.tv_reposts_icon
        private val tvCommentsCount = itemView.tv_comments_count
        private val tvCommentsIcon = itemView.tv_comments_icon

        private var context: Context = itemView.context
        private var resourses: Resources?

        init {
            resourses = context.resources

            tvCommentsIcon.typeface = mFontGoogle
            tvRepostIcon.typeface = mFontGoogle
            tvLikesIcon.typeface = mFontGoogle
        }

        override fun bindViewHolder(itemModel: NewsFeedViewModel) {
            val context = itemView.context
            bindHeader(context, itemModel)
            bindBody(context, itemModel)
            bindFooter(context, itemModel)
        }

        override fun unbindViewHolder() {
            unbindHeader()
            unbindBody()
            unbindFooter()
        }

        private fun bindHeader(context: Context, itemModel: NewsFeedViewModel) {
            //Загружаем изображение
            Glide.with(context)
                    .load(itemModel.mProfilePhoto)
                    .into(civProfileImage)

            tvName.text = itemModel.mProfileName

            val mIsReposts = itemModel.mIsReposts

            if (mIsReposts != null && mIsReposts) {
                ivRepostedIcon.visibility = View.VISIBLE
                tvRepostedProfileName.text = itemModel.mRepostsProfileName
            } else {
                ivRepostedIcon.visibility = View.GONE
                tvRepostedProfileName.text = null
            }
//            tvHeaderDate.text = formatDate(itemModel.mHeaderDate, context)
            tvHeaderDate.text = getFriendlyDateString(context,itemModel.mHeaderDate)
        }

        private fun unbindHeader() {
            civProfileImage.setImageBitmap(null)
            tvName.text = null
            tvRepostedProfileName.text = null
            tvHeaderDate.text = null
        }

        private fun bindBody(context: Context, itemModel: NewsFeedViewModel) {
            textViewBody.text = itemModel.textViewTextBody

            if (itemModel.mImageViewBody.isNotEmpty()) {

                Glide.with(context)
                        .load(itemModel.mImageViewBody)
                        .into(imageViewBody)
            } else {
                imageViewBody.visibility = View.GONE
            }



            if (itemModel.mSignerName.isNotEmpty()) {
                imageSignedIcon.text = context.getText(R.string.font_signed)
                imageSignerName.text = itemModel.mSignerName
            } else {
                imageSignedIcon.text = null
            }
        }

        private fun unbindBody() {
            textViewBody.text = null
            imageViewBody.setImageBitmap(null)
            imageSignedIcon.text = null
            imageSignerName.text = null
        }

        private fun bindFooter(context: Context, itemModel: NewsFeedViewModel) {
//            mTvFooterDate.text = formatDate(itemModel.mDateFooter, context)
            mTvFooterDate.text = getFriendlyDateString(context,itemModel.mDateFooter)

            bindData(tvLikesCount, tvLikesIcon, itemModel.mLikeCounterViewModel)
            bindData(tvCommentsCount, tvCommentsIcon, itemModel.mCommentsCounterViewModel)
            bindData(tvRepostCount, tvRepostIcon, itemModel.mRepostCounterViewModel)
        }

        private fun unbindFooter() {
            mTvFooterDate.text = null
            tvLikesCount.text = null
            tvRepostCount.text = null
            tvCommentsCount.text = null
        }

        private fun <T : CounterViewModel> bindData(tvCount: TextView,
                                                    tvIcon: TextView, item: T) {
            tvCount.text = item.mCount.toString()
            resourses?.getColor(item.mTextColor)?.let { tvCount.setTextColor(it) }
            resourses?.getColor(item.mTextColor)?.let(tvIcon::setTextColor)
        }
    }
}