package ua.rash1k.vkgroups.rest.api

import com.vk.sdk.api.VKApiConst
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ua.rash1k.vkgroups.CurrentUser
import ua.rash1k.vkgroups.rest.model.response.Full


interface AccountApi {

    @GET(ACCOUNT_REGISTER_DEVICE)
    fun registerDevice(@QueryMap map: HashMap<String, String>)
            : Observable<Full<Int>>

    @GET(ACCOUNT_GET_APP_PERMISSIONS)
    fun getAppPermissions(@Query("user_id") userId: String,
                          @Query(VKApiConst.ACCESS_TOKEN)
                          accessToken: String? = CurrentUser.getAccessToken(),
                          @Query(VKApiConst.VERSION)
                          apiVersion: String = "5.75"): Observable<Full<Int>>
}