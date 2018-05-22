package ua.rash1k.vkgroups.fcm.model

import android.support.annotation.DrawableRes
import ua.rash1k.vkgroups.models.Place


class PushModel(var mType: String = "",
                var mText: String?,
                var mTitle: String = "",
                var mFirstName: String = "",
                var mLastName: String = "",
                var mPlace: Place? = null,
                @DrawableRes
                var icon: Int = 0)


