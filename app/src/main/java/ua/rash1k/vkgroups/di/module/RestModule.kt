package ua.rash1k.vkgroups.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import ua.rash1k.vkgroups.rest.RestClient
import ua.rash1k.vkgroups.rest.api.*
import javax.inject.Singleton


//@Module(includes = [ContextModule::class])
@Module()
class RestModule {

    private val restClient: RestClient = RestClient

    @Singleton
    @Provides
    fun provideRestClient(): RestClient {
        return restClient
    }

    @Singleton
    @Provides
    fun provideWallApi(context: Application): WallApi {
        return restClient.createService(context, WallApi::class.java)
    }

    @Singleton
    @Provides
    fun provideUsersApi(context: Application): UsersApi {
        return restClient.createService(context, UsersApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMemberApi(context: Application): GroupsApi {
        return restClient.createService(context, GroupsApi::class.java)
    }


    @Singleton
    @Provides
    fun provideTopicsApi(context: Application): BoardApi {
        return restClient.createService(context, BoardApi::class.java)
    }

    @Singleton
    @Provides
    fun provideNewsFeedApi(context: Application): NewsFeedApi {
        return restClient.createService(context, NewsFeedApi::class.java)
    }

    @Singleton
    @Provides
    fun provideVideoApi(context: Application): VideoApi {
        return restClient.createService(context, VideoApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAccountApi(context: Application): AccountApi {
        return restClient.createService(context, AccountApi::class.java)
    }
}