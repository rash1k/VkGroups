package ua.rash1k.vkgroups.rest.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap
import ua.rash1k.vkgroups.models.Group
import ua.rash1k.vkgroups.models.Member
import ua.rash1k.vkgroups.rest.model.response.BaseItemResponse
import ua.rash1k.vkgroups.rest.model.response.Full


interface GroupsApi {


    @GET(GROUPS_GET_MEMBERS)
    fun getMembers(@QueryMap() map: HashMap<String, String>): Observable<Full<BaseItemResponse<Member>>>

    @GET(GROUPS_GET_BY_ID)
    fun getGroupsInfoById(@QueryMap() map: HashMap<String, String>): Observable<Full<List<Group>>>
}