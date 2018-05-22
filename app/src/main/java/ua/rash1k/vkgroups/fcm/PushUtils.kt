package ua.rash1k.vkgroups.fcm

import ua.rash1k.vkgroups.fcm.model.FcmMessage
import ua.rash1k.vkgroups.fcm.model.NewPostFcmMessage
import ua.rash1k.vkgroups.fcm.model.ReplyFcmMessage


object PushUtils {


    fun parseFcmMessage(sourceMessage: Map<String, String>): FcmMessage {
        var fcmMessage: FcmMessage? = null

        when (sourceMessage[FcmMessage.TYPE]) {
            FcmMessage.TYPE_NEW_POST -> {
                fcmMessage = NewPostFcmMessage(sourceMessage)
            }
            FcmMessage.TYPE_COMMENT_FOR_POST, FcmMessage.TYPE_ANSWER_FOR_MY_COMMENT -> {
                fcmMessage = ReplyFcmMessage(sourceMessage)
            }
        }
        return fcmMessage!!
    }

}