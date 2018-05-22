package ua.rash1k.vkgroups.fcm

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import ua.rash1k.vkgroups.R


class MyPreferenceManager(val context: Application) {

    val mSharedPreference: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(context)


    fun getPushNotificationNewPost(): Boolean =
            mSharedPreference.getBoolean(context.getString(
                    R.string.pref_push_notification_new_post_groups_key), true)

    fun getPushNotificationCommentEntity(): Boolean =
            mSharedPreference.getBoolean(context.getString(
                    R.string.pref_push_notification_comments_my_posts_key), false)


    fun getPushNotificationRepliesForComment(): Boolean =
            mSharedPreference.getBoolean(context.getString(
                    R.string.pref_push_notification_answers_to_my_comments_key), true)
}