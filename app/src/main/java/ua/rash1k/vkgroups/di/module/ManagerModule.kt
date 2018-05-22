package ua.rash1k.vkgroups.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import ua.rash1k.vkgroups.common.manager.MyFragmentManager
import ua.rash1k.vkgroups.common.manager.NetworkManager
import ua.rash1k.vkgroups.fcm.MyPreferenceManager
import javax.inject.Singleton

//Для предоставления MyFragmentManager
@Module()
 class ManagerModule {


    @Singleton
    @Provides
    fun provideMyFragmentManager(): MyFragmentManager {
        return MyFragmentManager()
    }


    @Singleton
    @Provides
    fun provideMyNetworkManager(): NetworkManager {
        return NetworkManager()
    }

    @Singleton
    @Provides
    fun provideMyPreferenceManager(context: Application): MyPreferenceManager {
        return MyPreferenceManager(context)
    }
}