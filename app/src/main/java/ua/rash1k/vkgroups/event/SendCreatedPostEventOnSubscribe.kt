package ua.rash1k.vkgroups.event

import com.vk.sdk.api.*
import com.vk.sdk.api.model.VKAttachments
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe


class SendCreatedPostEventOnSubscribe(val ownerId: Int,
                                      val postId: Int,
                                      val message: String,
                                      val attachment: VKAttachments) : ObservableOnSubscribe<VKResponse> {


    override fun subscribe(emitter: ObservableEmitter<VKResponse>) {
        val vkParameters = VKParameters()
        vkParameters[VKApiConst.OWNER_ID] = ownerId
        vkParameters[VKApiConst.POST_ID] = postId
        vkParameters[VKApiConst.MESSAGE] = message
        vkParameters[VKApiConst.ATTACHMENTS] = attachment


        val vkApi = VKApi.wall().post(vkParameters)

        vkApi.attempts = 10

        //отправляет запрос и создает слушатель для получения ответа
        vkApi.executeWithListener(object : VKRequest.VKRequestListener() {
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