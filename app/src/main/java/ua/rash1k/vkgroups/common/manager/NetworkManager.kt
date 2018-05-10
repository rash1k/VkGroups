package ua.rash1k.vkgroups.common.manager

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import io.reactivex.Observable
import io.realm.internal.Util
import ua.rash1k.vkgroups.MyApplication
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Callable
import javax.inject.Inject


//Проверяет имеет ли устройство доступ в интернет и доступен ли сервер VKApi
class NetworkManager {

    @Inject
    lateinit var context: Application

    companion object {
        val TAG = NetworkManager.javaClass.simpleName
    }

    init {
        MyApplication.sApplicationComponent.inject(this)
    }


    private fun isOnline(): Boolean {
        val cm =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null &&
                networkInfo.isConnected ||
                Util.isEmulator()
    }

    private fun isVkReachableCallable(): Callable<Boolean> {
        return Callable {
            try {
                when {
                    !isOnline() -> return@Callable false
                    else -> {
                        val url = URL("https://api.vk.com")
                        val urlc = url.openConnection() as HttpURLConnection
                        urlc.connectTimeout = 2000
                        urlc.connect()
                        return@Callable true
                    }
                }
            } catch (e: Exception) {
                return@Callable false
            }
        }
    }

    fun getNetworkObservable(): Observable<Boolean> {
        return Observable.fromCallable(isVkReachableCallable())
    }
}

