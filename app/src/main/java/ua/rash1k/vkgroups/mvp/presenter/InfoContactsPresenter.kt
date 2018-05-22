package ua.rash1k.vkgroups.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import io.reactivex.Observable
import io.realm.Realm
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.consts.ApiConstants
import ua.rash1k.vkgroups.models.Profile
import ua.rash1k.vkgroups.models.view.BaseViewModel
import ua.rash1k.vkgroups.models.view.MemberViewModel
import ua.rash1k.vkgroups.mvp.view.BaseFeedView
import ua.rash1k.vkgroups.rest.api.GroupsApi
import ua.rash1k.vkgroups.rest.api.UsersApi
import ua.rash1k.vkgroups.rest.model.request.GroupsGetByIdRequestModel
import ua.rash1k.vkgroups.rest.model.request.UsersGetRequestModel
import java.util.concurrent.Callable
import javax.inject.Inject

@InjectViewState
class InfoContactsPresenter : BaseFeedPresenter<BaseFeedView>() {


    @Inject
    lateinit var mGroupApi: GroupsApi

    @Inject
    lateinit var mUserApi: UsersApi


    init {
        MyApplication.sApplicationComponent.inject(this)
    }

    override fun onCreateLoadDataObservable(count: Int, offset: Int): Observable<BaseViewModel> {
        return mGroupApi.getGroupsInfoById(GroupsGetByIdRequestModel(ApiConstants.MY_GROUP_ID.toString()).toMap())
                .flatMap { full -> Observable.fromIterable(full.response) }
                .flatMap { group -> Observable.fromIterable(group.contactList) }
                .flatMap { contact -> mUserApi.get(UsersGetRequestModel(contact.userId.toString()).toMap()) }
                .flatMap { fullList -> Observable.fromIterable(fullList.response) }
                .doOnNext { profile -> profile.isContact = true }
                .doOnNext(::saveToDb)
                .flatMap { profile -> Observable.fromIterable(parsePojoModel(profile)) }
    }

    override fun onCreateRestoreDataObservable(): Observable<BaseViewModel> {
        return Observable.fromCallable(getListFromCallable())
                .flatMap { list -> Observable.fromIterable(parsePojoModel(list)) }
    }

    private fun parsePojoModel(listProfile: List<Profile>): List<BaseViewModel> {
        val items = arrayListOf<BaseViewModel>()
        for (profile in listProfile) {
            items.addAll(parsePojoModel(profile))
        }
        return items
    }

    private fun parsePojoModel(profile: Profile): List<BaseViewModel> {
        val items = arrayListOf<BaseViewModel>()
        items.add(MemberViewModel(profile))
        return items
    }

    private fun getListFromCallable(): Callable<List<Profile>> {
        return Callable {
            val realm = Realm.getDefaultInstance()
            val list = realm.where(Profile::class.java).equalTo("isContact", true)
                    .findAll()

            realm.copyFromRealm(list)
        }
    }
}