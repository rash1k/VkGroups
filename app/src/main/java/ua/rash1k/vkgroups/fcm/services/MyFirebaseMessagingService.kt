package ua.rash1k.vkgroups.fcm.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.fcm.MyPreferenceManager
import ua.rash1k.vkgroups.fcm.NotificationHelper
import ua.rash1k.vkgroups.fcm.PushUtils
import ua.rash1k.vkgroups.fcm.model.FcmMessage
import ua.rash1k.vkgroups.fcm.model.PushModel
import ua.rash1k.vkgroups.ui.activity.MainActivity
import javax.inject.Inject


class MyFirebaseMessagingService : FirebaseMessagingService() {

    val TAG = "MyFirebasService"

    @Inject
    lateinit var myPrefManager: MyPreferenceManager

    init {
        MyApplication.sApplicationComponent.inject(this)
    }

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    //Выполняется когда приходит сообщение
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]


        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "FCM Message ID: " + remoteMessage?.messageId)
        Log.d(TAG, "FCM Message TYPE: " + remoteMessage?.messageType)
        Log.d(TAG, "From Data Message: " + remoteMessage?.from)

        // Check if message contains a data payload.
        if (remoteMessage?.data?.isNotEmpty()!!) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)


            /* if (*//* Check if data needs to be processed by long running job *//* true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                scheduleJob()
            } else {
                // Handle message within 10 seconds
                handleNow(remoteMessage.data)
            }*/

        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.notification!!.body!!)
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

        sendNotification(remoteMessage)
    }
    // [END receive_message]


    //Отправка оповещения в наш UI
    private fun sendNotification(remoteMessage: RemoteMessage) {
        val pushModel: PushModel = PushUtils.parseFcmMessage(remoteMessage.data).toPushModel()


        if (pushModel.mType == FcmMessage.TYPE_ANSWER_FOR_MY_COMMENT &&
                !myPrefManager.getPushNotificationRepliesForComment()) return

        if (pushModel.mType == FcmMessage.TYPE_COMMENT_FOR_POST &&
                !myPrefManager.getPushNotificationCommentEntity()) return

        if (pushModel.mType == FcmMessage.TYPE_NEW_POST &&
                !myPrefManager.getPushNotificationNewPost()) return


        NotificationHelper.notify(this, pushModel)

    }


    /**
     * Schedule a job using FirebaseJobDispatcher.
     */
    private fun scheduleJob() {
        // [START dispatch_job]
        val dispatcher = FirebaseJobDispatcher(GooglePlayDriver(this))
        val myJob = dispatcher.newJobBuilder()
                .setService(MyJobService::class.java)
                .setTag("my-job-tag")
                .build()
        dispatcher.schedule(myJob)
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private fun handleNow(map: MutableMap<String, String>) {
        Log.d(TAG, "Short lived task is done.")
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private fun sendNotification(messageBody: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT)

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_message_white_24dp)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}