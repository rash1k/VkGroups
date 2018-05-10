package ua.rash1k.vkgroups.rest.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap
import ua.rash1k.vkgroups.models.Profile
import ua.rash1k.vkgroups.rest.model.response.Full


interface UsersApi {

    @GET(USERS_GET)
    fun get(@QueryMap() map: HashMap<String, String>): Observable<Full<List<Profile>>>
}