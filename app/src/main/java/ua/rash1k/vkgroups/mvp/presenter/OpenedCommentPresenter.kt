package ua.rash1k.vkgroups.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import io.reactivex.Observable
import io.realm.Realm
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.common.utils.getAttachmentViewModelItems
import ua.rash1k.vkgroups.models.CommentItem
import ua.rash1k.vkgroups.models.view.BaseViewModel
import ua.rash1k.vkgroups.models.view.CommentFooterViewModel
import ua.rash1k.vkgroups.models.view.OpenedPostHeaderViewModel
import ua.rash1k.vkgroups.mvp.view.BaseFeedView
import java.util.concurrent.Callable

@InjectViewState
class OpenedCommentPresenter : BaseFeedPresenter<BaseFeedView>() {

    var id: Int = 0


    init {
        MyApplication.sApplicationComponent.inject(this)
    }

    override fun onCreateLoadDataObservable(count: Int, offset: Int): Observable<BaseViewModel> {
        return createObservable()
    }

    override fun onCreateRestoreDataObservable(): Observable<BaseViewModel> {
        return createObservable()
    }


    private fun createObservable(): Observable<BaseViewModel> {
        return Observable.fromCallable(getCommentItemFromRealmCallable())
                .retry(2)
                .flatMap { commentItem ->
                    val list = arrayListOf<BaseViewModel>()
                    list.add(OpenedPostHeaderViewModel(commentItem))
                    list.addAll(getAttachmentViewModelItems(commentItem.attachments))
                    list.add(CommentFooterViewModel(commentItem))
                    Observable.fromIterable(list)
                }
    }

    private fun getCommentItemFromRealmCallable(): Callable<CommentItem> {
        return Callable<CommentItem> {
            val realm = Realm.getDefaultInstance()
            val commentItem = realm.where(CommentItem::class.java).equalTo("id", id).findFirst()

            realm.copyFromRealm(commentItem)
        }
    }
}
