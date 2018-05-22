package ua.rash1k.vkgroups.mvp.view

import com.arellomobile.mvp.MvpView
import ua.rash1k.vkgroups.models.Profile
import ua.rash1k.vkgroups.ui.fragment.BaseFragment


interface MainView : MvpView {
    //Показать окно авторизации
    fun startSignedIn()

    //Команда о том что прошла авторизация
    fun signedIn()

    //Вызывать из активи для заполнения Headers данными пользователя
    fun showCurrentUser(profile: Profile)

    fun showFragmentFromDrawer(baseFragment: BaseFragment)

    fun startActivityFromDrawer(act: Class<*>)
}