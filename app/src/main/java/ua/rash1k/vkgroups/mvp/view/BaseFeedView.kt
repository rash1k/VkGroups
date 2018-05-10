package ua.rash1k.vkgroups.mvp.view

import com.arellomobile.mvp.MvpView
import ua.rash1k.vkgroups.models.view.BaseViewModel


interface BaseFeedView : MvpView {

    //Показ анимации загрузки по свайпу вниз
    fun showRefreshing()

    //Скрытие загрузки
    fun hideRefreshing()

    //Показ анимации в центре, когда список загрузился
    fun showListProgress()

    fun hideListProgress()

    fun showError(messageError: String)

    //Замена списка новым
    fun setItems(items: List<BaseViewModel>)

    //Добавление новых элементов в конец списка
    fun addItems(items: List<BaseViewModel>)
}