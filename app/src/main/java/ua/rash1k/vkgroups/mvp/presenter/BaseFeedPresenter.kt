package ua.rash1k.vkgroups.mvp.presenter

import android.annotation.SuppressLint
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmObject
import ua.rash1k.vkgroups.common.manager.NetworkManager
import ua.rash1k.vkgroups.models.view.BaseViewModel
import ua.rash1k.vkgroups.mvp.presenter.BaseFeedPresenter.ProgressType.*
import ua.rash1k.vkgroups.mvp.view.BaseFeedView
import javax.inject.Inject


abstract class BaseFeedPresenter<V : BaseFeedView> : MvpPresenter<V>() {

    companion object {
        const val START_PAGE_SIZE = 15
        const val NEXT_PAGE_SIZE = 15
    }

    private var mIsLoading: Boolean = false

    @Inject
    lateinit var networkManager: NetworkManager

    //Будет инициировать загрузку данных
    //с помощью onCreateDataObservable
    @SuppressLint("CheckResult")
    private fun loadData(progressType: ProgressType, offset: Int, count: Int) {
        if (mIsLoading) return

        mIsLoading = true

        networkManager.getNetworkObservable()
                .flatMap { aBoolean: Boolean ->
                    //                    if (!aBoolean && start_from > 0) Observable.empty<Boolean>()

                    when {
                        !aBoolean && offset > 0 -> Observable.empty<Boolean>()
                        aBoolean -> onCreateLoadDataObservable(count, offset)
                        else -> onCreateRestoreDataObservable()
                    }
                }

                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe({ _ -> onLoadStart(progressType) })
                .doFinally { onLoadFinish(progressType) }
                .subscribe({ repositories ->
                    onLoadingSuccess(progressType, repositories as MutableList<BaseViewModel>)
                }, { error ->
                    error.printStackTrace()
                    onLoadingFailed(error)
                })
    }

    //Метод для создания Observable, который излучает данные взятые из сети
    abstract fun onCreateLoadDataObservable(count: Int, offset: Int): Observable<BaseViewModel>

    //Для восстановления данных из базы данных
    abstract fun onCreateRestoreDataObservable(): Observable<BaseViewModel>

    enum class ProgressType {
        Refreshing, ListProgress, Paging
    }

    //Метод в зависимости от типа загрузки будет показывать различные ProgressBar
    private fun showProgress(progressType: ProgressType) {
        when (progressType) {
            ListProgress -> viewState.showListProgress()
            Refreshing -> viewState.showRefreshing()
            else -> { }
        }
    }

    private fun hideProgress(progressType: ProgressType) {
        when (progressType) {
            Refreshing -> viewState.hideRefreshing()
            ListProgress -> viewState.hideListProgress()
            else -> {
            }
        }
    }

    //Будет вызываться при первой загрузки
    fun loadStart() {
        loadData(ProgressType.ListProgress, 0, START_PAGE_SIZE)
    }

    //Вызывается при загрузке новых элементов при прокрутки
    fun loadNext(offset: Int) {
        loadData(Paging, offset, NEXT_PAGE_SIZE)
    }

    //При обновлении списка
    fun loadRefresh() {
        loadData(Refreshing, 0, START_PAGE_SIZE)
    }

    private fun onLoadStart(progressType: ProgressType) {
        showProgress(progressType)
    }

    private fun onLoadFinish(progressType: ProgressType) {
        mIsLoading = false
        hideProgress(progressType)
    }

    private fun onLoadingFailed(throwable: Throwable) {
        viewState.showError(throwable.message ?: "")
    }

    private fun onLoadingSuccess(progressType: ProgressType, items: MutableList<BaseViewModel>) {
        when (progressType) {
            Paging -> viewState.addItems(items)
            else -> {
                viewState.setItems(items)
            }
        }
    }

    //Вызывать будем тогда, когда хотим сохранить данные
    fun saveToDb(item: RealmObject) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction { _realm -> _realm.copyToRealmOrUpdate(item) }
    }
}