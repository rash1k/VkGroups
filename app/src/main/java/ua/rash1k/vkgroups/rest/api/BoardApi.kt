package ua.rash1k.vkgroups.rest.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap
import ua.rash1k.vkgroups.models.CommentItem
import ua.rash1k.vkgroups.models.Topic
import ua.rash1k.vkgroups.rest.model.response.BaseItemResponse
import ua.rash1k.vkgroups.rest.model.response.Full
import ua.rash1k.vkgroups.rest.model.response.ItemWithSendersResponse


interface BoardApi {

    @GET(BOARD_GET_TOPICS)
    fun getTopics(@QueryMap() map: HashMap<String, String>):
            Observable<Full<BaseItemResponse<Topic>>>

    @GET(BOARD_GET_COMMENTS)
    fun getComments(@QueryMap() map: HashMap<String, String>):
            Observable<Full<ItemWithSendersResponse<CommentItem>>>

}