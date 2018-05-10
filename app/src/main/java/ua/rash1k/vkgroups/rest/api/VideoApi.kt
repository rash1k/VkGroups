package ua.rash1k.vkgroups.rest.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap
import ua.rash1k.vkgroups.rest.model.response.Full
import ua.rash1k.vkgroups.rest.model.response.VideoResponse


interface VideoApi {


    @GET(VIDEO_GET)
    fun get(@QueryMap map: Map<String, String>): Observable<Full<VideoResponse>>

}