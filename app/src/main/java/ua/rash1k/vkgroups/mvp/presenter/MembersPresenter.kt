package ua.rash1k.vkgroups.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.consts.ApiConstants
import ua.rash1k.vkgroups.models.Member
import ua.rash1k.vkgroups.models.view.BaseViewModel
import ua.rash1k.vkgroups.models.view.MemberViewModel
import ua.rash1k.vkgroups.mvp.view.BaseFeedView
import ua.rash1k.vkgroups.rest.api.GroupsApi
import ua.rash1k.vkgroups.rest.model.request.GroupsMembersRequestModel
import java.util.concurrent.Callable
import javax.inject.Inject

@InjectViewState
class MembersPresenter : BaseFeedPresenter<BaseFeedView>() {


    @Inject
    lateinit var mGroupsApi: GroupsApi

    init {
        MyApplication.sApplicationComponent.inject(this)
    }


    override fun onCreateLoadDataObservable(count: Int, offset: Int): Observable<BaseViewModel> {
        return mGroupsApi.getMembers(GroupsMembersRequestModel(
               ApiConstants.LIS_HIM_GROUP_NAME, count = count, offset = offset).toMap())
                .flatMap { baseItemResponseFull ->
                    Observable.fromIterable(baseItemResponseFull.response?.items)
                }
                .doOnNext { member -> saveToDb(member) }
                .map { member ->
                    //val baseItems = arrayListOf<BaseViewModel>()
                    MemberViewModel(member)
                }
    }

    override fun onCreateRestoreDataObservable(): Observable<BaseViewModel> {
        return Observable.fromCallable(getListFromRealmCallable())
                .flatMap { listMember -> Observable.fromIterable(listMember) }
                .map { member -> MemberViewModel(member) }
    }

    private fun getListFromRealmCallable(): Callable<List<Member>> {
        return Callable {
            val sortFields = arrayOf(Member.ID)
            val sortOrder = arrayOf(Sort.ASCENDING)
            val realm = Realm.getDefaultInstance()
            val realmResults: RealmResults<Member> = realm.where(Member::class.java)
                    .sort(sortFields, sortOrder)
                    .findAll()
            realm.copyFromRealm(realmResults)
        }
    }
}