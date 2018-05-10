package ua.rash1k.vkgroups.rest

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


//Используется для инициализации Retrofit и Api запросов


object RestClient {
    private const val VK_BASE_URL = "https://api.vk.com/method/"


    private fun getHttpClient(context: Context): OkHttpClient {

        val file = File(context.cacheDir, "HttpCache")
        val cache = Cache(file, 10 * 1000 * 1000)


        return  OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .cache(cache)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS).build()
    }


    fun <S> createService(context: Context, serviceClass: Class<S>): S {

        //Потом создаем сам Retrofit для работы с сетью.
        //-> Далее добавляем фабрику для возврата объекта Call который содержит
        //преднастроенные адреса вызовов для запроса например Call.()...
        return Retrofit.Builder()
                .client(getHttpClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(VK_BASE_URL)
                .build()
                .create(serviceClass)
        // Но мы будем использовать RxJava чтобы за нас делала все Rx-библиотека

    }
}