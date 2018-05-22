package ua.rash1k.vkgroups.mvp.presenter

import android.annotation.SuppressLint
import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmObject
import ua.rash1k.vkgroups.CurrentUser
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.common.manager.MyFragmentManager
import ua.rash1k.vkgroups.common.manager.NetworkManager
import ua.rash1k.vkgroups.common.utils.saveIdToInternalDir
import ua.rash1k.vkgroups.models.Profile
import ua.rash1k.vkgroups.mvp.view.MainView
import ua.rash1k.vkgroups.rest.api.AccountApi
import ua.rash1k.vkgroups.rest.api.UsersApi
import ua.rash1k.vkgroups.rest.model.request.AccountRegisterDeviceRequest
import ua.rash1k.vkgroups.rest.model.request.UsersGetRequestModel
import ua.rash1k.vkgroups.ui.activity.SettingActivity
import ua.rash1k.vkgroups.ui.fragment.*
import java.util.concurrent.Callable
import javax.inject.Inject


//Сущность ViewState хранит список команд которые передаются во ViewState(вызываются) из Presenter во View
//Для доступа ко View используем ссылку viewState
@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    @Inject
    lateinit var myFragmentManager: MyFragmentManager

    @Inject
    lateinit var mUsersApi: UsersApi

    @Inject
    lateinit var mNetworkManager: NetworkManager

    @Inject
    lateinit var mAccountApi: AccountApi

    init {
        MyApplication.sApplicationComponent.inject(this)
    }

    fun checkAuth() {
        if (!CurrentUser.isAuthorized()) {
            viewState.startSignedIn()
        } else {
            getCurrentUser()
            viewState.signedIn()
        }
    }

    fun registerDeviceToVkServer(context: Context) {
        //Регистрируем устройство на серевере VK для получения PUSH-уведомлений
        val deviceId = saveIdToInternalDir(context)

        mAccountApi.registerDevice(AccountRegisterDeviceRequest(deviceId = deviceId)
                .toMap())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    //Получаем данные профиля пользователя
    @SuppressLint("CheckResult")
    private fun getProfileFromNetwork(): Observable<Profile> {
        /*
        * Метод get возвращает Observable<Full<List<Profile>>>, и с помощью оператора flatMap
        * трансофрмируем из списка в Observable<Profile> и сохраняем
        * */
        return mUsersApi.get(UsersGetRequestModel(CurrentUser.getUserId() ?: "").toMap())
                .flatMap { item -> io.reactivex.Observable.fromIterable(item.response) }
                .doOnNext { profile -> saveToDb(profile) }
    }

    private fun getProfileFromDb(): Observable<Profile> {
        /*
        * Трансформирует данные из Callable<Profile> в Observable<Profile>*/
        return Observable.fromCallable(getProfileFromRealmCallable())
    }

    private fun saveToDb(item: RealmObject) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction { realm1 -> realm1.copyToRealmOrUpdate(item) }
    }

    private fun getProfileFromRealmCallable(): Callable<Profile> {
        return Callable<Profile> {
            val realm = Realm.getDefaultInstance()
            val realmResults: Profile? = realm.where(Profile::class.java)
                    .equalTo("id", CurrentUser.getUserId()?.toInt())
                    .findFirst()
            realm.copyFromRealm(realmResults)
        }
    }

    @SuppressLint("CheckResult")
    private fun getCurrentUser() {
        //Проверяем доступность сети к серверу в контакте и брать данные из сети или базы данных
        mNetworkManager.getNetworkObservable()
                .flatMap { boolean1 ->
                    if (!CurrentUser.isAuthorized()) {
                        viewState.startSignedIn()
                    }
                    if (boolean1) getProfileFromNetwork() else getProfileFromDb()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ profile -> viewState.showCurrentUser(profile) },
                        { error -> error.printStackTrace() })
    }

    fun drawerItemClick(id: Int) {
        var fragment: BaseFragment? = null

        when (id) {
            1 -> fragment = NewsFeedFragment()
//          1 -> fragment = NewsFragment()
            2 -> fragment = MyPostFragment()
            3 -> viewState.startActivityFromDrawer(SettingActivity::class.java)
//            4 -> fragment = GroupsFragment()
            5 -> fragment = MemberFragment()
            6 -> fragment = TopicFragment()
            7 -> fragment = InfoFragment()
        }

        if (fragment != null && !myFragmentManager.isAlreadyContains(fragment)) {
            viewState.showFragmentFromDrawer(fragment)
        }
    }
}