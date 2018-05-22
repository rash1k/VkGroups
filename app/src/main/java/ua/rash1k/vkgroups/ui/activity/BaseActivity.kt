package ua.rash1k.vkgroups.ui.activity


import android.os.Bundle
import android.support.annotation.LayoutRes
import android.widget.ProgressBar
import com.arellomobile.mvp.MvpAppCompatActivity
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.common.manager.MyFragmentManager
import ua.rash1k.vkgroups.ui.fragment.BaseFragment
import javax.inject.Inject

abstract class BaseActivity : MvpAppCompatActivity() {

    internal open val TAG = BaseActivity::class.java.name

    //Выступает в роли делегата
    //Dagger будет брать инициализацию из пакета module
    @Inject
    lateinit var myFragmentManager: MyFragmentManager

    lateinit var mProgressBar: ProgressBar
//   private val mToolBar: Toolbar = toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        setSupportActionBar(toolbar)

        MyApplication.sApplicationComponent.inject(this)
        mProgressBar = findViewById(R.id.progressBar)

//        myFragmentManager = MyFragmentManager()

        //Инфлейтим метод который будут переопределять в дочерних классах
        layoutInflater.inflate(getMainContentLayout(), main_wrapper)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        removeCurrentFragment()
    }

    //Аннотация предполагает что будет возвращать ссылку на этот Layout
    @LayoutRes
    protected abstract fun getMainContentLayout(): Int

    //Меняет заголовок тулбара и видимость кнопки FAB
    fun fragmentOnScreen(fragment: BaseFragment?) {
        if (fragment != null) {
            setToolbarTitle(fragment.createToolbarTitle(this))
            setupFabVisibility(fragment.needFab())
        }
    }

    //Метод установки title Toolbar
    private fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    private fun setupFabVisibility(needFab: Boolean) {
        if (fab == null) return
        if (needFab) fab.show() else fab.hide()
    }

    //Установка корневого фрагмента
    protected fun setRootContent(baseFragment: BaseFragment) {
        myFragmentManager.setRootFragment(this, baseFragment, R.id.main_wrapper)
    }

    fun addFragment(baseFragment: BaseFragment) {
        myFragmentManager.addFragment(this, baseFragment, R.id.main_wrapper)
    }

    private fun removeCurrentFragment(): Boolean {
        return myFragmentManager.removeCurrentFragment(this)
    }

    fun removeFragment(baseFragment: BaseFragment): Boolean {
        return myFragmentManager.removeFragment(this, baseFragment)
    }

}
