package ua.rash1k.vkgroups.event

import android.util.Log
import com.vk.sdk.api.VKApi
import com.vk.sdk.api.VKError
import com.vk.sdk.api.VKRequest
import com.vk.sdk.api.VKResponse
import com.vk.sdk.api.model.VKApiDocument
import com.vk.sdk.api.model.VKDocsArray
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import java.io.File


class UploadFileEventOnSubscribe(val file: File) : ObservableOnSubscribe<VKApiDocument> {


    override fun subscribe(emitter: ObservableEmitter<VKApiDocument>) {
        VKApi.docs().uploadWallDocRequest(file)
                .executeWithListener(object : VKRequest.VKRequestListener() {

                    override fun onComplete(response: VKResponse?) {
                        super.onComplete(response)
                        val vkApiDoc = (response?.parsedModel as VKDocsArray)[0]
                        /* val vkApiDoc =
                                 (response?.json?.getJSONObject("response")
                                         ?.getJSONArray("items")!![0] as VKDocsArray)[0]*/

                        emitter.onNext(vkApiDoc)
                        emitter.onComplete()
                    }

                    override fun onError(error: VKError?) {
                        super.onError(error)
                        Log.d("TAG", error?.apiError?.errorMessage)
                        emitter.onError(error?.httpError!!)
                    }
                })
    }
}
