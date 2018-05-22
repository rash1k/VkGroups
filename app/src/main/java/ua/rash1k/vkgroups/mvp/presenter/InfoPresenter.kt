package ua.rash1k.vkgroups.mvp.presenter

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import io.reactivex.Observable
import io.realm.Realm
import io.realm.Sort
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.consts.ApiConstants
import ua.rash1k.vkgroups.models.Group
import ua.rash1k.vkgroups.models.view.BaseViewModel
import ua.rash1k.vkgroups.models.view.info.InfoContactsViewModel
import ua.rash1k.vkgroups.models.view.info.InfoLinksViewModel
import ua.rash1k.vkgroups.models.view.info.InfoStatusViewModel
import ua.rash1k.vkgroups.mvp.view.BaseFeedView
import ua.rash1k.vkgroups.rest.api.GroupsApi
import ua.rash1k.vkgroups.rest.model.request.GroupsGetByIdRequestModel
import java.util.concurrent.Callable
import javax.inject.Inject


@InjectViewState
class InfoPresenter : BaseFeedPresenter<BaseFeedView>() {

     val TAG = "InfoPresenter"

    @Inject
    lateinit var mGroupAPi: GroupsApi

    init {
        MyApplication.sApplicationComponent.inject(this)
    }

    override fun onCreateLoadDataObservable(count: Int, offset: Int): Observable<BaseViewModel> {
        return mGroupAPi.getGroupsInfoById(GroupsGetByIdRequestModel(ApiConstants.LIS_HIM_GROUP_NAME.toString()).toMap())
                .flatMap { full -> Observable.fromIterable(full.response) }
                .doOnNext { saveToDb(it) }
                .flatMap { group -> Observable.fromIterable(parsePojoModel(group)) }
    }

    override fun onCreateRestoreDataObservable(): Observable<BaseViewModel> {
        return Observable.fromCallable(getGroupFromRealmCallable())
                .flatMap { group -> Observable.fromIterable(parsePojoModel(group)) }
    }


    private fun parsePojoModel(group: Group): List<BaseViewModel> {
        val baseItems = arrayListOf<BaseViewModel>()
        Log.d(TAG,"parsePojoModel: ${group.description}")
        baseItems.add(InfoStatusViewModel(group))
        baseItems.add(InfoContactsViewModel())
        baseItems.add(InfoLinksViewModel())
        return baseItems
    }

    private fun getGroupFromRealmCallable(): Callable<out Group> {
        return Callable<Group> {
            val sortFields = arrayOf("id")
            val sortOrder = arrayOf(Sort.ASCENDING)
            val realm = Realm.getDefaultInstance()
            /*val realmResults: RealmResults<Group> = realm.where(Group::class.java)
                    .sort(sortFields, sortOrder)
                    .findFirst()*/
            val group: Group? = realm.where(Group::class.java)
                    .equalTo("id", Math.abs(ApiConstants.MY_GROUP_ID))
//                    .sort(sortFields, sortOrder)
                    .findFirst()

            realm.copyFromRealm(group)
        }
    }
}