package ua.rash1k.vkgroups.event

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.vk.sdk.VKAccessToken
import com.vk.sdk.api.VKApi
import com.vk.sdk.api.VKError
import com.vk.sdk.api.VKRequest
import com.vk.sdk.api.VKResponse
import com.vk.sdk.api.model.VKApiPhoto
import com.vk.sdk.api.model.VKPhotoArray
import com.vk.sdk.api.photo.VKImageParameters
import com.vk.sdk.api.photo.VKUploadImage
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe


class UploadPhotoEventOnSubscribe(val photoUrl: String) : ObservableOnSubscribe<VKApiPhoto> {

    override fun subscribe(emitter: ObservableEmitter<VKApiPhoto>) {
        val bitmapFactoryOptions = BitmapFactory.Options()
        bitmapFactoryOptions.inPreferredConfig = Bitmap.Config.ARGB_8888
        val bitmap = BitmapFactory.decodeFile(photoUrl, bitmapFactoryOptions)


        VKApi.uploadWallPhotoRequest(VKUploadImage(bitmap, VKImageParameters.pngImage()),
                VKAccessToken.currentToken().userId.toLong(), 0)
                .executeWithListener(object : VKRequest.VKRequestListener() {

                    override fun onComplete(response: VKResponse?) {
                        super.onComplete(response)
                        val vkApiPhoto = (response?.parsedModel as VKPhotoArray)[0]
                        /* val vkApiDoc =
                                 (response?.json?.getJSONObject("response")
                                         ?.getJSONArray("items")!![0] as VKDocsArray)[0]*/

                        emitter.onNext(vkApiPhoto)
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