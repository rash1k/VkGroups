package ua.rash1k.vkgroups.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.common.utils.getAttachmentViewModelItems
import ua.rash1k.vkgroups.common.utils.getWallList
import ua.rash1k.vkgroups.consts.ApiConstants
import ua.rash1k.vkgroups.models.WallItem
import ua.rash1k.vkgroups.models.view.BaseViewModel
import ua.rash1k.vkgroups.models.view.NewsItemFooterViewModel
import ua.rash1k.vkgroups.models.view.OpenedPostHeaderViewModel
import ua.rash1k.vkgroups.models.view.OpenedPostRepostHeaderViewModel
import ua.rash1k.vkgroups.mvp.view.OpenedPostView
import ua.rash1k.vkgroups.rest.api.WallApi
import ua.rash1k.vkgroups.rest.model.request.WallGetByIdRequestModel
import java.util.*
import java.util.concurrent.Callable
import javax.inject.Inject


@InjectViewState
class OpenedPostPresenter : BaseFeedPresenter<OpenedPostView>() {


    var id: Int = 0

    @Inject
    lateinit var mWallApi: WallApi

    val TAG = "OpenedPostPresenter"

    init {
        MyApplication.sApplicationComponent.inject(this)
    }

    override fun onCreateLoadDataObservable(count: Int, offset: Int): Observable<BaseViewModel> {
        return mWallApi.getWallById(WallGetByIdRequestModel(ApiConstants.MY_GROUP_ID, id).toMap())
                .flatMap { full ->
                    Observable.fromIterable(getWallList(full.response!!))
                }

                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext({ wallItem ->
                    val newsItemFooterViewModel = NewsItemFooterViewModel(wallItem)

                    viewState.setFooter(newsItemFooterViewModel)
                })

                .observeOn(Schedulers.io())
                .doOnNext(::saveToDb)
                .flatMap { wallItem ->
                    val list = ArrayList<BaseViewModel>()
                    val forwardedList = ArrayList<BaseViewModel>()

                    list.add(OpenedPostHeaderViewModel(wallItem))

                    list.addAll(getAttachmentViewModelItems(wallItem.attachments))
                    if (wallItem.haveSharedRepost()) {

                        forwardedList.add(OpenedPostRepostHeaderViewModel(wallItem.getSharedRepost()!!))
                        forwardedList.addAll(getAttachmentViewModelItems(wallItem.getSharedRepost()?.attachments!!))
                    }
                    Observable.fromIterable(list).concatWith(Observable.fromIterable(forwardedList))
                }
    }


    override fun onCreateRestoreDataObservable(): Observable<BaseViewModel> {

        return Observable.fromCallable(getListFromRealmCallable())

                .createDataObservable()
    }

    private fun getListFromRealmCallable(): Callable<WallItem> {
        return Callable<WallItem> {

            val realm = Realm.getDefaultInstance()
            val wallItem = realm.where(WallItem::class.java).equalTo("id", id).findFirst()

            realm.copyFromRealm(wallItem!!)
        }
    }


    private fun Observable<WallItem>.createDataObservable(): Observable<BaseViewModel> {
        return this.observeOn(AndroidSchedulers.mainThread())
                .doOnNext { wallItem ->
                    val newsItemFooterViewModel = NewsItemFooterViewModel(wallItem)
                    viewState.setFooter(newsItemFooterViewModel)
                }
                .observeOn(Schedulers.io())
                .doOnNext(::saveToDb)
                .flatMap { wallItem ->
                    val list = arrayListOf<BaseViewModel>()
                    val forwardedList = arrayListOf<BaseViewModel>()

                    list.add(OpenedPostHeaderViewModel(wallItem))
                    list.addAll(getAttachmentViewModelItems(wallItem.attachments))

                    if (wallItem.haveSharedRepost()) {
                        forwardedList.add(OpenedPostRepostHeaderViewModel(wallItem))
                        forwardedList.addAll(getAttachmentViewModelItems(wallItem.getSharedRepost()?.attachments!!))

                    }
                    Observable.fromIterable(list).concatWith(Observable.fromIterable(forwardedList))
                }
    }

}