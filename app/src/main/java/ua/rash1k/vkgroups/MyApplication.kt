package ua.rash1k.vkgroups

import android.app.Application
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader
import com.mikepenz.materialdrawer.util.DrawerImageLoader
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKAccessTokenTracker
import com.vk.sdk.VKSdk
import io.realm.Realm
import io.realm.RealmConfiguration
import ua.rash1k.vkgroups.common.utils.toast
import ua.rash1k.vkgroups.di.component.ApplicationComponent
import ua.rash1k.vkgroups.di.component.DaggerApplicationComponent
import ua.rash1k.vkgroups.di.module.AppModule


class MyApplication : Application() {

    var vkAccessTokenTracker: VKAccessTokenTracker = object : VKAccessTokenTracker() {
        override fun onVKAccessTokenChanged(oldToken: VKAccessToken?, newToken: VKAccessToken?) {
            if (newToken == null) {
             toast("$oldToken is invalid")
            }
        }
    }


    companion object {
        @JvmStatic  //platformStatic allow access it from java code
        lateinit var sApplicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        //init VkApi
        vkAccessTokenTracker.startTracking()
        VKSdk.initialize(this)
        //init Dagger 2
        buildComponent()
        //init Realm
        initRealm()
        //initDrawerImageLoader
        initDrawerImageLoader()
    }

    private fun buildComponent() {
        sApplicationComponent =
                DaggerApplicationComponent.builder()
                        .appModule(AppModule(this))
//                        .managerModule()
//                        .contextModule(ContextModule(this.applicationContext))
//                                        .presenterModule(PresenterModule())
//                                        .viewModule(ViewModule())
                        .build()
    }


    private fun initRealm() {
        Realm.init(this)
        val realmConfig = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(realmConfig)
    }

    private fun initDrawerImageLoader() {
        DrawerImageLoader.init(object : AbstractDrawerImageLoader() {
            override fun set(imageView: ImageView, uri: Uri, placeholder: Drawable, tag: String) {
//                super.set(imageView, uri, placeholder, tag)

                Glide.with(imageView.context)
                        .load(uri)
                        .into(imageView)
            }
        })
    }
}