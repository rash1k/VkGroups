package ua.rash1k.vkgroups.rest.api

import android.util.Log
import com.vk.sdk.api.*
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import org.json.JSONException
import ua.rash1k.vkgroups.models.countable.Likes


class LikeEventOnSubscribe(var likes: Likes,
                           var mType: String,
                           var ownerId: Int,
                           var mItemId: Int) : ObservableOnSubscribe<Int> {


    val TAG: String = "LikeEventOnSubscribe"

    override fun subscribe(emitter: ObservableEmitter<Int>) {
        if (likes.canLike == 1) {
            addLike(emitter)
        } else if (likes.canLike == 0 && likes.isUserLikes()) {
            deleteLike(emitter)
        } else {
            emitter.onComplete()
        }
    }

    private fun addLike(emitter: ObservableEmitter<Int>) {
        Log.d(TAG, "onComplete: 1 " + Thread.currentThread())

        addLike(mType, ownerId, mItemId, object : VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse?) {
                super.onComplete(response)
                Log.d(TAG, "onComplete: 2 " + Thread.currentThread())

                try {
                    val likesCount = response?.json?.getJSONObject("response")?.getInt("likes")
                    emitter.onNext(likesCount!!)
                    emitter.onComplete()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            override fun onError(error: VKError?) {
                super.onError(error)
                if (error != null) {
                    emitter.onError(error.httpError)
                }
            }
        })
    }

    private fun deleteLike(emitter: ObservableEmitter<Int>) {
        deleteLike(mType, ownerId, mItemId, object : VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse?) {
                super.onComplete(response)
                try {
                    val countLikes = response?.json?.getJSONObject("response")
                            ?.getInt("likes")

                    emitter.onNext(countLikes!!)
                    emitter.onComplete()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            override fun onError(error: VKError?) {
                super.onError(error)
                emitter.onError(error?.httpError!!)
            }
        })
    }


    private fun addLike(type: String,
                        ownerId: Int,
                        itemId: Int,
                        listener: VKRequest.VKRequestListener) {

        val vkRequest = VKRequest("likes.add", VKParameters.from("type", type,
                VKApiConst.OWNER_ID, ownerId,
                "item_id", itemId))

        vkRequest.attempts = 10
        vkRequest.executeWithListener(listener)
    }

    private fun deleteLike(type: String,
                           ownerId: Int,
                           itemId: Int,
                           listener: VKRequest.VKRequestListener) {

        val vkRequest = VKRequest("likes.delete", VKParameters.from("type", type,
                VKApiConst.OWNER_ID, ownerId,
                "item_id", itemId))

        vkRequest.attempts = 10
        vkRequest.executeWithListener(listener)
    }

}