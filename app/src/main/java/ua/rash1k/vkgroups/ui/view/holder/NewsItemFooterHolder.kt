package ua.rash1k.vkgroups.ui.view.holder

import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmObject
import kotlinx.android.synthetic.main.item_news_footer.view.*
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.common.manager.MyFragmentManager
import ua.rash1k.vkgroups.common.utils.formatDate
import ua.rash1k.vkgroups.common.utils.getWallList
import ua.rash1k.vkgroups.models.Place
import ua.rash1k.vkgroups.models.WallItem
import ua.rash1k.vkgroups.models.countable.Likes
import ua.rash1k.vkgroups.models.view.NewsItemFooterViewModel
import ua.rash1k.vkgroups.models.view.counter.CounterViewModel
import ua.rash1k.vkgroups.models.view.counter.LikeCounterViewModel
import ua.rash1k.vkgroups.rest.api.LikeEventOnSubscribe
import ua.rash1k.vkgroups.rest.api.WallApi
import ua.rash1k.vkgroups.rest.model.request.WallGetByIdRequestModel
import ua.rash1k.vkgroups.rest.model.response.GetWallByIdResponse
import ua.rash1k.vkgroups.ui.activity.BaseActivity
import ua.rash1k.vkgroups.ui.fragment.CommentsFragment
import java.util.concurrent.Callable
import javax.inject.Inject

class NewsItemFooterHolder(view: View) : BaseViewHolder<NewsItemFooterViewModel>(view) {

    private val POST: String = "post"

    private val tvDate = view.tv_date
    private val tvLikesCount = view.tv_likes_count
    private val tvLikesIcon = view.tv_likes_icon
    private val tvRepostCount = view.tv_reposts_count
    private val tvRepostIcon = view.tv_reposts_icon
    private val tvCommentsCount = view.tv_comments_count
    private val tvCommentsIcon = view.tv_comments_icon

    private val rlComments: View = view.rl_comments
    private val rlLikes: View = view.rl_likes
    private val rlRepost: View = view.rl_reposts

    private var context: Context = view.context
    private var resourses: Resources?

    @Inject
    lateinit var mFontGoogle: Typeface

    @Inject
    lateinit var myFragmentManager: MyFragmentManager

    @Inject
    lateinit var mWallApi: WallApi

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

        rlLikes.setOnClickListener {
            like(itemModel)
        }
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
                                                tvIcon: TextView, itemModel: T) {
        tvCount.text = itemModel.mCount.toString()
        resourses?.getColor(itemModel.mTextColor)?.let { tvCount.setTextColor(it) }
        resourses?.getColor(itemModel.mTextColor)?.let(tvIcon::setTextColor)
    }

    override fun unbindViewHolder() {
        tvDate.text = null
        tvLikesCount.text = null
        tvRepostCount.text = null
        tvCommentsCount.text = null
    }

    private fun like(itemModel: NewsItemFooterViewModel) {
        val wallItem = getWallItemFromRealm(itemModel.mId)
        if (wallItem?.likes != null) {
            likeObservable(wallItem.ownerId, wallItem.id, wallItem.likes!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ likes ->
                        itemModel.mLikeCounterViewModel = likes
                        bindLikes(likes)
                    }, { error -> error.printStackTrace() })
        }
    }


    private fun likeObservable(ownerId: Int, postId: Int, like: Likes): Observable<LikeCounterViewModel> {
        return Observable.create(LikeEventOnSubscribe(like, POST, ownerId, postId))
                .observeOn(Schedulers.io())
                .flatMap { count ->
                    mWallApi.getWallById(WallGetByIdRequestModel(ownerId, postId).toMap())
                }
                .flatMap { full: GetWallByIdResponse ->
                    Observable.fromIterable(getWallList(full.response!!))
                }
                .doOnNext(::saveToDB)
                .map { wallItem -> LikeCounterViewModel(wallItem.likes) }
    }

    private fun getWallItemFromRealm(postId: Int): WallItem? {
        val realm = Realm.getDefaultInstance()
        val wallItem =
                realm.where(WallItem::class.java).equalTo("id", postId).findFirst()

        return if (wallItem != null) realm.copyFromRealm(wallItem) else null
    }

    private fun getCallableWallItemFromRealm(postId: Int): Callable<WallItem> {
        return Callable {
            val realm = Realm.getDefaultInstance()
            val wallItem =
                    realm.where(WallItem::class.java).equalTo("id", postId).findFirst()

//            return if (wallItem != null) realm.copyFromRealm(wallItem) else null

            realm.copyFromRealm(wallItem!!)
        }
    }

    private fun saveToDB(item: RealmObject) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction { realm1 -> realm1.copyToRealmOrUpdate(item) }
    }
}
