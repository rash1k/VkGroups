package ua.rash1k.vkgroups.rest.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap
import ua.rash1k.vkgroups.models.CommentItem
import ua.rash1k.vkgroups.rest.model.response.Full
import ua.rash1k.vkgroups.rest.model.response.GetWallByIdResponse
import ua.rash1k.vkgroups.rest.model.response.GetWallResponse
import ua.rash1k.vkgroups.rest.model.response.ItemWithSendersResponse


interface WallApi {

    /*
    * ownerId - идент группы или пользователя
    *access token - токен доступа
    * extended - возвращение доп. полей profiles and groups(Инфа о пользователях и сообществах)
    * */

    @GET(WALL_GET)
    fun get(@QueryMap() map: HashMap<String, String>): Observable<GetWallResponse>

    @GET(WALL_GET_BY_ID)
    fun getWallById(@QueryMap() map: HashMap<String, String>): Observable<GetWallByIdResponse>


    @GET(WALL_GET_COMMENTS)
    fun getComments(@QueryMap() map: HashMap<String, String>): Observable<Full<ItemWithSendersResponse<CommentItem>>>


    /*  @GET(WALL_GET)
      fun get(@Query("owner_id") ownerId: String,

              @Query("access_token") accessToken: String,

              @Query("extended") extended: Int,

              @Query("v") version: String): Call<Full<BaseItemResponse<WallItem>>>*/
}