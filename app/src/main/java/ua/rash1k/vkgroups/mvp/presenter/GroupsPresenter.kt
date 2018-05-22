package ua.rash1k.vkgroups.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.Sort
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.common.utils.getGroupsList
import ua.rash1k.vkgroups.models.Group
import ua.rash1k.vkgroups.models.view.BaseViewModel
import ua.rash1k.vkgroups.models.view.GroupsViewModel
import ua.rash1k.vkgroups.mvp.view.BaseFeedView
import ua.rash1k.vkgroups.rest.api.GroupsApi
import ua.rash1k.vkgroups.rest.api.GroupsEventOnSubscribe
import ua.rash1k.vkgroups.rest.model.request.GroupsMembersRequestModel
import java.util.concurrent.Callable
import javax.inject.Inject

@InjectViewState
class GroupsPresenter : BaseFeedPresenter<BaseFeedView>() {

    val TAG = "GroupsPresenter"


    @Inject
    lateinit var mGroupsApi: GroupsApi

    var appId: Int = 0

    init {
        MyApplication.sApplicationComponent.inject(this)
    }


    override fun onCreateLoadDataObservable(count: Int, offset: Int): Observable<BaseViewModel> {
        return Observable.create(GroupsEventOnSubscribe(offset = offset, count = count))
                .observeOn(Schedulers.io())
                .flatMap { vkResponse ->
                    Observable.fromIterable(getGroupsList(vkResponse))
                }
                .doOnNext { group -> saveToDb(group) }
                .doOnNext { group ->
                    mGroupsApi.getMembers(GroupsMembersRequestModel(
                            group.screenName, count = 0, offset = 0).toMap())
                            .map { t -> group.subscribeCount = t.response?.count!! }

                }
                .flatMap { group -> Observable.fromIterable(parsePojoModel(group)) }

    }


    /* private fun getGroupsObservable(group: Group): Observable<Group> {
         return mGroupsApi.getMembers(GroupsMembersRequestModel(
                 group.screenName, count = 0, offset = 0).toMap())
                 .flatMap { full -> }
     }*/

/* private fun checkAppPermissionsForStats(group: Group): Observable<Group> {
val statsPermissions = 1048576 *//*Доступ к статистике групп и приложений пользователя,
                                        администратором которых он является.*//*

        return mAccountApi.getAppPermissions(CurrentUser.getUserId()!!)
                .map { full ->
                    Log.d(TAG, "response: ${full.response!!}")

                    val perm = full.response!! and statsPermissions
                    Log.d(TAG, "perm: $perm")

                    (full.response!! and statsPermissions) != 0
                }
    }*/


    override fun onCreateRestoreDataObservable(): Observable<BaseViewModel> {
        return Observable.fromCallable(getGroupFromRealmCallable())
                .flatMap { groups -> Observable.fromIterable(parsePojoModel(groups)) }
    }

    private fun getGroupFromRealmCallable(): Callable<List<Group>> {
        return Callable {
            val sortFields = arrayOf("id")
            val sortOrder = arrayOf(Sort.ASCENDING)
            val realm = Realm.getDefaultInstance()
            val result = realm.where(Group::class.java)
                    .sort(sortFields, sortOrder)
                    .findAll()


            realm.copyFromRealm(result)!!
        }
    }

    private fun parsePojoModel(listGroups: List<Group>): List<BaseViewModel> {
        val items = arrayListOf<BaseViewModel>()
        for (group in listGroups) {
            items.addAll(parsePojoModel(group))
        }
        return items
    }

    private fun parsePojoModel(group: Group): List<BaseViewModel> {
        val items = arrayListOf<BaseViewModel>()
        items.add(GroupsViewModel(group))
        return items
    }

}