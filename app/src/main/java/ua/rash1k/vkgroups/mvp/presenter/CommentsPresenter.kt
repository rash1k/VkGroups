package ua.rash1k.vkgroups.mvp.presenter


import com.arellomobile.mvp.InjectViewState
import io.reactivex.Observable
import io.realm.Realm
import io.realm.Sort
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.common.utils.getCommentList
import ua.rash1k.vkgroups.models.CommentItem
import ua.rash1k.vkgroups.models.Place
import ua.rash1k.vkgroups.models.view.BaseViewModel
import ua.rash1k.vkgroups.models.view.CommentBodyViewModel
import ua.rash1k.vkgroups.models.view.CommentFooterViewModel
import ua.rash1k.vkgroups.models.view.CommentHeaderViewModel
import ua.rash1k.vkgroups.mvp.view.BaseFeedView
import ua.rash1k.vkgroups.rest.api.WallApi
import ua.rash1k.vkgroups.rest.model.request.WallGetCommentsRequestModel
import java.util.*
import java.util.concurrent.Callable
import javax.inject.Inject

@InjectViewState
class CommentsPresenter : BaseFeedPresenter<BaseFeedView>() {

    lateinit var mPlace: Place

    @Inject
    lateinit var mWallApi: WallApi


    init {
        MyApplication.sApplicationComponent.inject(this)
    }

    override fun onCreateLoadDataObservable(count: Int, offset: Int): Observable<BaseViewModel> {
        return mWallApi.getComments(WallGetCommentsRequestModel(
                Integer.parseInt(mPlace.ownerId), Integer.parseInt(mPlace.postId), offset).toMap())
                .flatMap { full ->
                    Observable.fromIterable(getCommentList(full.response!!))
                }
                .doOnNext({ commentItem -> commentItem.place = mPlace })
                .doOnNext(::saveToDb)
                .flatMap { commentItem -> Observable.fromIterable(parsePojoModel(commentItem)) }
    }

    override fun onCreateRestoreDataObservable(): Observable<BaseViewModel> {
        return Observable.fromCallable<List<CommentItem>>(listFromRealmCallable())
                .flatMap { listCommentItem -> Observable.fromIterable(listCommentItem) }
                .filter { commentItem ->
                    commentItem.place!! == this.mPlace && !commentItem.isFromTopic
                }
                .flatMap { commentItem ->
                    Observable.fromIterable(parsePojoModel(commentItem))
                }
    }

    private fun listFromRealmCallable(): Callable<List<CommentItem>> {
        return Callable {
            val sortFields = arrayOf("id")
            val sortOrder = arrayOf(Sort.ASCENDING)

            val realm = Realm.getDefaultInstance()
            val results = realm.where(CommentItem::class.java)
                    .sort(sortFields, sortOrder)
                    .findAll()
            realm.copyFromRealm(results)
        }
    }


    private fun parsePojoModel(commentItem: CommentItem): List<BaseViewModel> {
        val baseItems = ArrayList<BaseViewModel>()
        baseItems.add(CommentHeaderViewModel(commentItem))
        baseItems.add(CommentBodyViewModel(commentItem))
        baseItems.add(CommentFooterViewModel(commentItem))
        return baseItems
    }


    fun setPlace(place: Place) {
        this.mPlace = place
    }
}