package ua.rash1k.vkgroups.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.TaskStackBuilder
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.fcm.model.FcmMessage
import ua.rash1k.vkgroups.fcm.model.PushModel
import ua.rash1k.vkgroups.models.Place
import ua.rash1k.vkgroups.ui.activity.OpenedPostFromPushActivity
import java.util.*


object NotificationHelper {


    fun notify(context: Context, pushModel: PushModel?) {
        if (pushModel == null) return

        val mId = Date().time.toInt()

        val smallIconRes: Int = if (pushModel.icon != 0) pushModel.icon else R.drawable.ic_message_white_24dp

        val title: String = if (pushModel.mFirstName != null &&
                pushModel.mLastName != null) pushModel.mFirstName + pushModel.mLastName else "Test message"

        val text = if (pushModel.mText != null) pushModel.mText else "Message"


        when (pushModel.mType) {
            FcmMessage.TYPE_ANSWER_FOR_MY_COMMENT -> {
                title.plus("отвечает:")
            }
            FcmMessage.TYPE_COMMENT_FOR_POST -> {
                title.plus("комментрирует:")
            }

            FcmMessage.TYPE_NEW_POST -> {
                title.plus("новая запись на стене")
            }
        }

        val openedIntent = Intent(context, OpenedPostFromPushActivity::class.java)
        openedIntent.putExtra(Place.PLACE, pushModel.mPlace?.toBundle())

        val taskStackBuilder = TaskStackBuilder.create(context)
        taskStackBuilder.addParentStack(OpenedPostFromPushActivity::class.java)
        taskStackBuilder.addNextIntent(openedIntent)
        val pendingIntent =
                taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)


        val channelId = context.getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(smallIconRes)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                    title,
                    NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())

    }
}