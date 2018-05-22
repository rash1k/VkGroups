package ua.rash1k.vkgroups.rest.api

import com.vk.sdk.api.*
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import ua.rash1k.vkgroups.CurrentUser
import ua.rash1k.vkgroups.consts.ApiConstants


class GroupsEventOnSubscribe(var offset: Int = 0,
                             var count: Int = ApiConstants.DEFAULT_COUNT) : ObservableOnSubscribe<VKResponse> {


    override fun subscribe(emitter: ObservableEmitter<VKResponse>) {
        val vkParameters = VKParameters()
        vkParameters[VKApiConst.USER_ID] = CurrentUser.getUserId()
        vkParameters[VKApiConst.OFFSET] = offset
        vkParameters[VKApiConst.EXTENDED] = 1
        vkParameters[VKApiConst.COUNT] = count

        val vkRequest = VKApi.groups().get(vkParameters)
        vkRequest.attempts = 5


        vkRequest.executeWithListener(object : VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse?) {
                super.onComplete(response)
                emitter.onNext(response!!)
                emitter.onComplete()
            }


            override fun onError(error: VKError?) {
                super.onError(error)
                emitter.onError(error?.httpError!!)
            }
        })

    }
}