package ua.rash1k.vkgroups.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.SectionDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IProfile
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.common.utils.toast
import ua.rash1k.vkgroups.consts.ApiConstants
import ua.rash1k.vkgroups.models.Profile
import ua.rash1k.vkgroups.mvp.presenter.MainPresenter
import ua.rash1k.vkgroups.mvp.view.MainView
import ua.rash1k.vkgroups.rest.api.AccountApi
import ua.rash1k.vkgroups.ui.fragment.BaseFragment
import ua.rash1k.vkgroups.ui.fragment.NewsFeedFragment
import javax.inject.Inject


class MainActivity : BaseActivity(), MainView {


    override val TAG = MainActivity::class.java.name

    private lateinit var mDrawer: Drawer
    private lateinit var mAccountHeader: AccountHeader

    //Служит для управления жизненным циклом презентера
    @InjectPresenter
    lateinit var mMainPresenter: MainPresenter

    @Inject
    lateinit var mAccountApi: AccountApi


    override fun onCreate(savedInstanceState: Bundle?) {
        //Вызывает метод суперкласса BaseActivity
        MyApplication.sApplicationComponent.inject(this)
        super.onCreate(savedInstanceState)


        mMainPresenter.checkAuth()


    }

    override fun startSignedIn() {
        VKSdk.login(this, *ApiConstants.DEFAULT_LOGIN_SCOPE)
    }

    override fun signedIn() {
        setRootContent(NewsFeedFragment())
//        setRootContent(NewsFragment())
//        setRootContent(GroupsFragment())
        setUpDrawer()

        /* //Регистрируем устройство на серевере VK для получения PUSH-уведомлений
         mAccountApi.registerDevice(AccountRegisterDeviceRequest(deviceId = getDeviceId(this))
                 .toMap())
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe()*/
        mMainPresenter.registerDeviceToVkServer(this)

    }


    override fun startActivityFromDrawer(act: Class<*>) {
        startActivity(Intent(this, act))
    }

    private fun setUpDrawer() {
        //Add items in Drawer
        val item1 = PrimaryDrawerItem().withIdentifier(1)
                .withName(R.string.screen_name_news_feed_title).withIcon(GoogleMaterial.Icon.gmd_home)
        val item2 = PrimaryDrawerItem().withIdentifier(2)
                .withName(R.string.screen_name_my_posts).withIcon(GoogleMaterial.Icon.gmd_list)
        val item3 = PrimaryDrawerItem().withIdentifier(3)
                .withName(R.string.screen_name_settings).withIcon(GoogleMaterial.Icon.gmd_settings)

        val item4 = PrimaryDrawerItem().withIdentifier(4)
                .withName(R.string.screen_name_list_of_groups)
                .withIcon(GoogleMaterial.Icon.gmd_people_outline)

        val item5 = PrimaryDrawerItem().withIdentifier(5)
                .withName(R.string.screen_name_members).withIcon(GoogleMaterial.Icon.gmd_people)
        val item6 = PrimaryDrawerItem().withIdentifier(6)
                .withName(R.string.screen_name_topics).withIcon(GoogleMaterial.Icon.gmd_record_voice_over)
        val item7 = PrimaryDrawerItem().withIdentifier(7)
                .withName(R.string.screen_name_info).withIcon(GoogleMaterial.Icon.gmd_info)
        val item8 = PrimaryDrawerItem().withIdentifier(8)
                .withName(R.string.screen_name_rules).withIcon(GoogleMaterial.Icon.gmd_assignment)

        mAccountHeader = AccountHeaderBuilder()
                .withActivity(this)
                .build()

        val mToolbar: Toolbar = findViewById(R.id.toolbar)

        mDrawer = DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withTranslucentStatusBar(true)//If true статусбар становится прозрачным при открытии Drawer
                .withActionBarDrawerToggle(true)//If true добавляет иконку в Toolbar
                .withAccountHeader(mAccountHeader)
                .addDrawerItems(item1, item2, item3, SectionDrawerItem().withName("Groups"),
                        item4, item5, item6, item7, item8)

                .withOnDrawerItemClickListener { view, position,
                                                 drawerItem ->
                    mMainPresenter.drawerItemClick(drawerItem.identifier.toInt())
                    false
                }
                .build()
    }

    override fun showFragmentFromDrawer(baseFragment: BaseFragment) {
        setRootContent(baseFragment)
    }


    override fun getMainContentLayout(): Int {
        return R.layout.activity_main
    }

    override fun showCurrentUser(profile: Profile) {
        val profileDrawersItem = mutableListOf<IProfile<*>>()
        profileDrawersItem.add(ProfileDrawerItem()
                .withName(profile.getFullName())
                .withEmail(VKAccessToken.currentToken().email)
                .withIcon(profile.getDisplayProfilePhoto()))


        profileDrawersItem.add(ProfileDrawerItem().withName(getString(R.string.current_logout))
                .withOnDrawerItemClickListener { _: View, _: Int, _: IDrawerItem<*, *> ->
                    mAccountHeader.removeProfile(0)
                    mAccountHeader.removeProfile(0)
                    VKSdk.logout()
                    false
                })

        mAccountHeader.profiles = profileDrawersItem
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken> {
                    override fun onResult(res: VKAccessToken) {
                        Log.d(TAG, "Error: ${res.accessToken}")
                        // Пользователь успешно авторизовался
                        mMainPresenter.checkAuth()
                    }

                    // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
                    override fun onError(error: VKError) {
                        toast("Error: $error")
                        Log.d(TAG, "Error: $error")
                    }
                })) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
