package ua.rash1k.vkgroups.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.common.utils.getNewsList
import ua.rash1k.vkgroups.models.News
import ua.rash1k.vkgroups.models.view.BaseViewModel
import ua.rash1k.vkgroups.models.view.NewsFeedViewModel
import ua.rash1k.vkgroups.rest.api.NewsFeedApi
import ua.rash1k.vkgroups.rest.model.request.NewsFeedRequestModel
import java.util.concurrent.Callable
import javax.inject.Inject

const val TAG = "NewsPresenter"

@InjectViewState
class NewsPresenter : NewsFeedPresenter() {


    @Inject
    lateinit var mNewsFeedApi: NewsFeedApi

    init {
        MyApplication.sApplicationComponent.inject(this)
    }

    override fun onCreateLoadDataObservable(count: Int, offset: Int): Observable<BaseViewModel> {
        return mNewsFeedApi.get(NewsFeedRequestModel(count = count, start_from = offset).toMap())
                .flatMap { newsFeed ->
                    Observable.fromIterable(getNewsList(newsFeed.response))
                }
                .doOnNext { news -> saveToDb(news) }
                .map { newsItem -> NewsFeedViewModel(newsItem) }
    }

    override fun onCreateRestoreDataObservable(): Observable<BaseViewModel> {
        return Observable.fromCallable(getListFromRealmCallable())
                .flatMap { newsList -> Observable.fromIterable(newsList) }
                .map { itemNews -> NewsFeedViewModel(itemNews) }


        /* return Observable.fromIterable(getListFromRealm())
                 .map { t -> NewsFeedViewModel(t) }*/


    }

    private fun getListFromRealmCallable(): Callable<List<News>> {
        return Callable {
            val sortFields = arrayOf("date")
            val sortOrder = arrayOf(Sort.DESCENDING)
            val realm = Realm.getDefaultInstance()
            val realmResults: RealmResults<News> = realm.where(News::class.java)
                    .sort(sortFields, sortOrder)
                    .findAll()
            realm.copyFromRealm(realmResults)
        }
    }

    private fun getListFromRealm(): List<News> {
        val sortFields = arrayOf("date")
        val sortOrder = arrayOf(Sort.DESCENDING)
        val realm = Realm.getDefaultInstance()
        val realmResults: RealmResults<News> = realm.where(News::class.java)
                .sort(sortFields, sortOrder)
                .findAll()
        return realm.copyFromRealm(realmResults)
    }
}