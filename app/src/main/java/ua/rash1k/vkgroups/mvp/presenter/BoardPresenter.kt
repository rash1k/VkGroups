package ua.rash1k.vkgroups.mvp.presenter

import android.annotation.SuppressLint
import com.arellomobile.mvp.InjectViewState
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.consts.ApiConstants
import ua.rash1k.vkgroups.models.Topic
import ua.rash1k.vkgroups.models.view.BaseViewModel
import ua.rash1k.vkgroups.models.view.TopicViewModel
import ua.rash1k.vkgroups.mvp.view.BaseFeedView
import ua.rash1k.vkgroups.rest.api.BoardApi
import ua.rash1k.vkgroups.rest.model.request.BoardGetRequestModel
import java.util.concurrent.Callable
import javax.inject.Inject

@InjectViewState
class BoardPresenter : BaseFeedPresenter<BaseFeedView>() {


    @Inject
    lateinit var mBoardApi: BoardApi


    init {
        MyApplication.sApplicationComponent.inject(this)
    }

    @SuppressLint("CheckResult")
    override fun onCreateLoadDataObservable(count: Int, offset: Int): Observable<BaseViewModel> {

        return mBoardApi.getTopics(BoardGetRequestModel(ApiConstants.MY_GROUP_ID,
                count = count, offset = offset).toMap())
                .flatMap { full ->
                    Observable.fromIterable(full.response?.items)
                }
                .doOnNext { it.groupId = ApiConstants.MY_GROUP_ID } ///?????
                .doOnNext(this::saveToDb)
                .map { topic -> TopicViewModel(topic) }
    }

    override fun onCreateRestoreDataObservable(): Observable<BaseViewModel> {
        return Observable.fromCallable(getListFromRealmCallable())
                .flatMap { listTopics -> Observable.fromIterable(listTopics) }
                .map { topics -> TopicViewModel(topics) }
    }


    private fun getListFromRealmCallable(): Callable<List<Topic>> {

        return Callable<List<Topic>> {
            val sortFields = arrayOf("id")
            val sortOrder = arrayOf(Sort.ASCENDING)
            val realm = Realm.getDefaultInstance()

            val realmResults: RealmResults<Topic> = realm.where(Topic::class.java)
                    .equalTo("groupId", ApiConstants.MY_GROUP_ID)
                    .sort(sortFields, sortOrder)
                    .findAll()

            realm.copyFromRealm(realmResults)
        }

    }
}










