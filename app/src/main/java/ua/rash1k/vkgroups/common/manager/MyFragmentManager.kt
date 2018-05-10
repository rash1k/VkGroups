package ua.rash1k.vkgroups.common.manager

import android.support.annotation.IdRes
import android.support.v4.app.FragmentTransaction
import ua.rash1k.vkgroups.ui.activity.BaseActivity
import ua.rash1k.vkgroups.ui.fragment.BaseFragment
import java.util.*


class MyFragmentManager {

    companion object {
        private const val EMPTY_FRAGMENT_STACK_SIZE: Int = 1
    }

    //Stack для фрагментов
    private var mFragmentStack = Stack<BaseFragment>()
    private var mCurrentFragment: BaseFragment? = null


    //Для установки корневого фрагмента и отображения заголовка тулбара и кнопки FAB
    fun setRootFragment(activity: BaseActivity?, fragment: BaseFragment, @IdRes containerId: Int) {

//        mCurrentFragment = fragment

        if (activity != null && !activity.isFinishing && !isAlreadyContains(fragment)) {
            val fragmentTransaction =
                    createAddTransaction(activity, fragment, addToBackStack = false)
            fragmentTransaction.replace(containerId, fragment)
            commitAddTransaction(activity, fragment, fragmentTransaction, addToBackStack = false)

/*
            //Очищаем Stack и добавляем фрагмент
            clearStackAddFragment(fragment)
            commitTransaction(activity, fragmentTransaction)*/
        }
    }

    //Добавляет фрагменты поверх корневого
    //Они будут открываться из пунктов меню навигации
    fun addFragment(activity: BaseActivity?, fragment: BaseFragment, @IdRes containerId: Int) {
        if (activity != null && !activity.isFinishing && !isAlreadyContains(fragment)) {
            val fragmentTransaction =
                    createAddTransaction(activity, fragment, addToBackStack = true)

            fragmentTransaction.add(containerId, fragment)
            commitAddTransaction(activity, fragment, fragmentTransaction, addToBackStack = true)
        }
    }

    fun removeCurrentFragment(activity: BaseActivity): Boolean {
        return removeFragment(activity, mCurrentFragment)
    }

    fun removeFragment(activity: BaseActivity, fragment: BaseFragment?): Boolean {
        val canRemove = fragment != null && mFragmentStack.size > EMPTY_FRAGMENT_STACK_SIZE

        if (canRemove) {
            //Удаляем верхний в списке элемент
            mFragmentStack.pop()
            //Последний эл. в списке
            mCurrentFragment = mFragmentStack.lastElement()

            val transaction =
                    activity.supportFragmentManager.beginTransaction().remove(fragment)
            commitTransaction(activity, transaction)
        }
        return canRemove
    }

    private fun createAddTransaction(activity: BaseActivity, fragment: BaseFragment,
                                     addToBackStack: Boolean): FragmentTransaction {

        val fragmentTransaction: FragmentTransaction =
                activity.supportFragmentManager.beginTransaction()

        if (addToBackStack) fragmentTransaction.addToBackStack(fragment.tag)
        return fragmentTransaction
    }

    private fun commitAddTransaction(activity: BaseActivity, fragment: BaseFragment,
                                     fragmentTransaction: FragmentTransaction?,
                                     addToBackStack: Boolean) {
        //Сохраняем текущий фрагмент
        if (fragmentTransaction != null) {
            mCurrentFragment = fragment
        }

        //Очищаем Stack
        if (!addToBackStack) mFragmentStack = Stack()
        mFragmentStack.add(fragment)

        commitTransaction(activity, fragmentTransaction)
    }

    //Добавляется в BackStack в любом случае
    private fun commitTransaction(activity: BaseActivity,
                                  fragmentTransaction: FragmentTransaction?) {
        fragmentTransaction?.commit()

        //Show current fragment
        activity.fragmentOnScreen(mCurrentFragment)
    }

    internal fun isAlreadyContains(baseFragment: BaseFragment?): Boolean {
        if (baseFragment == null) return false

        return mCurrentFragment != null && mCurrentFragment?.javaClass?.name == baseFragment.javaClass.name
    }

    private fun isActivityAndFragmentNonNull(baseActivity: BaseActivity?,
                                             baseFragment: BaseFragment): Boolean {
        return baseActivity != null &&
                !baseActivity.isFinishing &&
                !isAlreadyContains(baseFragment)
    }


    private fun clearStackAddFragment(baseFragment: BaseFragment) {
        mFragmentStack = Stack()
        mFragmentStack.push(baseFragment)
    }
}