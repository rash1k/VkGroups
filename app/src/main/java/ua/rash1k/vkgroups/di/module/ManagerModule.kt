package ua.rash1k.vkgroups.di.module

import dagger.Module
import dagger.Provides
import ua.rash1k.vkgroups.common.manager.MyFragmentManager
import ua.rash1k.vkgroups.common.manager.NetworkManager
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
}