package ua.rash1k.vkgroups.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import io.reactivex.Observable
import io.realm.Realm
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.consts.ApiConstants
import ua.rash1k.vkgroups.models.Group
import ua.rash1k.vkgroups.models.view.BaseViewModel
import ua.rash1k.vkgroups.models.view.attachment.LinkAttachmentViewModel
import ua.rash1k.vkgroups.mvp.view.BaseFeedView
import ua.rash1k.vkgroups.rest.api.GroupsApi
import ua.rash1k.vkgroups.rest.model.request.GroupsGetByIdRequestModel
import java.util.concurrent.Callable
import javax.inject.Inject

@InjectViewState
class InfoLinksPresenter : BaseFeedPresenter<BaseFeedView>() {


    @Inject
    lateinit var mGroupApi: GroupsApi

    init {
        MyApplication.sApplicationComponent.inject(this)
    }


    override fun onCreateLoadDataObservable(count: Int, offset: Int): Observable<BaseViewModel> {
        return mGroupApi.getGroupsInfoById(GroupsGetByIdRequestModel(ApiConstants.MY_GROUP_ID.toString()).toMap())
                .flatMap { groupList -> Observable.fromIterable(groupList.response) }
                .doOnNext(::saveToDb)
                .flatMap { group -> Observable.fromIterable(parsePojoModel(group)) }

    }

    override fun onCreateRestoreDataObservable(): Observable<BaseViewModel> {
        return Observable.fromCallable(getGroupFromRealmCallable())
                .flatMap { group -> Observable.fromIterable(parsePojoModel(group)) }
    }

    private fun getGroupFromRealmCallable(): Callable<Group> {
        return Callable {
            val realm = Realm.getDefaultInstance()
            val result = realm.where(Group::class.java).equalTo("id",
                    Math.abs(ApiConstants.MY_GROUP_ID)).findFirst()
            realm.copyFromRealm(result)!!
        }
    }


    private fun parsePojoModel(group: Group): List<BaseViewModel> {
        val listItem = arrayListOf<BaseViewModel>()
        for (link in group.links) {
            listItem.add(LinkAttachmentViewModel(link))
        }
        return listItem
    }
}