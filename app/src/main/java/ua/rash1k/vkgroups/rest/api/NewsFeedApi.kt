package ua.rash1k.vkgroups.rest.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap
import ua.rash1k.vkgroups.models.NewsFeed


interface NewsFeedApi {

    @GET(NEWS_FEED_GET)
    fun get(@QueryMap map: Map<String, String>): Observable<NewsFeed>
}