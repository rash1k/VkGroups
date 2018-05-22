package ua.rash1k.vkgroups.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import ua.rash1k.vkgroups.ui.activity.BaseActivity


//Этот класс выполянет роль в View в библиотеке Moxy и привязывает своё состояние(State) к Presenter
abstract class BaseFragment : MvpAppCompatFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getMainContentLayout(), container, false)
    }


    fun createToolbarTitle(context: Context): String {
        return context.getString(onCreateToolbarTitle())
    }

    //Будет запрашивать Layout у дочерних фрагментов
    @LayoutRes
    protected abstract fun getMainContentLayout(): Int

    //Будет запрашивать заголовок у дочерних фрагментов
    @StringRes
    protected abstract fun onCreateToolbarTitle(): Int

    fun getBaseActivity(): BaseActivity {
        return activity as BaseActivity
    }

    open fun needFab(): Boolean = false

}