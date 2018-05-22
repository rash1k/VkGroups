package ua.rash1k.vkgroups.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import ua.rash1k.vkgroups.CurrentUser
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.common.utils.getWallList
import ua.rash1k.vkgroups.models.WallItem
import ua.rash1k.vkgroups.models.view.BaseViewModel
import ua.rash1k.vkgroups.models.view.NewsItemBodyViewModel
import ua.rash1k.vkgroups.models.view.NewsItemFooterViewModel
import ua.rash1k.vkgroups.models.view.NewsItemHeaderViewModel
import ua.rash1k.vkgroups.mvp.view.BaseFeedView
import ua.rash1k.vkgroups.rest.api.WallApi
import ua.rash1k.vkgroups.rest.model.request.WallGetRequestModel
import java.util.concurrent.Callable
import javax.inject.Inject

@InjectViewState
open class NewsFeedPresenter : BaseFeedPresenter<BaseFeedView>() {


    var enableIdFiltering: Boolean = false

    @Inject
    lateinit var mWallApi: WallApi

    init {
        MyApplication.sApplicationComponent.inject(this)
    }

    //Проверяет условие что фильтр включен и ID текущего пользователя не null
    //И если условие соблюдается возвращает по ID Observable
    //Или неизменынный Obsevable если false
    open fun applyFiltering(): ObservableTransformer<WallItem, WallItem> {
        return if (enableIdFiltering && CurrentUser.getUserId() != null) {
            ObservableTransformer { baseItemObservable ->
                baseItemObservable.filter({ wallItem ->
                    CurrentUser.getUserId() == wallItem.fromId.toString()
                })
            }
        } else {
            ObservableTransformer { baseItemObservable ->
                baseItemObservable
            }
        }
    }

    override fun onCreateLoadDataObservable(count: Int, offset: Int): Observable<BaseViewModel> {
        return mWallApi.get(WallGetRequestModel(count = count, offset = offset).toMap())
                .flatMap { response ->
                    Observable.fromIterable(getWallList(response.response!!))
                }
                //С помощью оператора compose трансофрмируют Rx-цеаочку в Observable
                .compose(applyFiltering())
                .doOnNext { wallItem: WallItem -> saveToDb(wallItem) }//Сохраняем данные

                .flatMap { wallItem: WallItem ->
                    val baseItems = arrayListOf<BaseViewModel>()
                    baseItems.add(NewsItemHeaderViewModel(wallItem))
                    baseItems.add(NewsItemBodyViewModel(wallItem))
                    baseItems.add(NewsItemFooterViewModel(wallItem))
                    Observable.fromIterable(baseItems)
                }
    }

    override fun onCreateRestoreDataObservable(): Observable<BaseViewModel> {
        return Observable.fromCallable(getListFromRealmCallable())
                .flatMap { list -> Observable.fromIterable(list) }
                .compose(applyFiltering())
                .flatMap { wallItem -> Observable.fromIterable(parsePojoModel(wallItem)) }
    }

    private fun getListFromRealmCallable(): Callable<List<WallItem>> {

        return Callable<List<WallItem>> {
            val sortFields = arrayOf("date")
            val sortOrder = arrayOf(Sort.DESCENDING)
            val realm = Realm.getDefaultInstance()
            val realmResults: RealmResults<WallItem> = realm.where(WallItem::class.java)
                    .sort(sortFields, sortOrder)
                    .findAll()
            realm.copyFromRealm(realmResults)
        }
    }

    private fun parsePojoModel(wallItem: WallItem): List<BaseViewModel> {
        val baseItems = arrayListOf<BaseViewModel>()
        baseItems.add(NewsItemHeaderViewModel(wallItem))
        baseItems.add(NewsItemBodyViewModel(wallItem))
        baseItems.add(NewsItemFooterViewModel(wallItem))
        return baseItems
    }
}