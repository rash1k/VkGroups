package ua.rash1k.vkgroups.common.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Parcelable
import android.support.annotation.IdRes
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.vk.sdk.api.model.VKAttachments
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.models.attachment.ApiAttachment
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


const val SECONDS_IN_MINUTE: Long = 60
const val SECONDS_IN_HOUR = SECONDS_IN_MINUTE * 60
const val SECONDS_IN_DAY = SECONDS_IN_HOUR * 24


fun Context.toast(text: CharSequence, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, text, duration).show()
}

val Context.inflator
    get() = LayoutInflater.from(this)


fun <T : View> Activity.bindView(@IdRes id: Int) = lazy { findViewById<T>(id) }

fun <T : Parcelable> Activity.getExtraParcelable(key: String) =
        lazy { intent.extras.getParcelable<T>(key) }

fun Activity.getExtraString(key: String) =
        lazy { intent.extras.getString(key) }


inline fun <reified T : Activity> Context.startActivity() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}

fun convertAttachmentsToFontIcons(attachments: List<ApiAttachment>): String {

    return buildString {

        for (attachment in attachments) {
            when (attachment.type) {
                VKAttachments.TYPE_PHOTO -> append(charArrayOf(0xE251.toChar()))
                VKAttachments.TYPE_AUDIO -> append(charArrayOf(0xE310.toChar()))
                VKAttachments.TYPE_VIDEO -> append(charArrayOf(0xE02C.toChar()))
                VKAttachments.TYPE_LINK -> append(charArrayOf(0xE250.toChar()))
                VKAttachments.TYPE_DOC -> append(charArrayOf(0xE24D.toChar()))
            }
        }
    }
}


//fun convertAttachmentsToFontIcons(attachments: List<ApiAttachment>): String {
//    var attachmentsString = ""
//
//    for (attachment in attachments) {
//        when (attachment.type) {
//            VKAttachments.TYPE_PHOTO -> attachmentsString += String(charArrayOf(0xE251.toChar())) + " "
//            VKAttachments.TYPE_AUDIO -> attachmentsString += String(charArrayOf(0xE310.toChar())) + " "
//            VKAttachments.TYPE_VIDEO -> attachmentsString += String(charArrayOf(0xE02C.toChar())) + " "
//            VKAttachments.TYPE_LINK -> attachmentsString += String(charArrayOf(0xE250.toChar())) + " "
//            VKAttachments.TYPE_DOC -> attachmentsString += String(charArrayOf(0xE24D.toChar())) + " "
//        }
//    }
//    return attachmentsString
//}

fun formatDate(date: Long, context: Context): String {

    val currentLocale: Locale = context.resources.configuration.locale

    val currentDate: Date = Date(date * 1000)
//    val currentDate: Date = Date(date)

    val callendar = Calendar.getInstance()
    callendar.time = currentDate

    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val currentDayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)

    var simpleDateFormat = SimpleDateFormat("dd.MM.yy в H:mm", currentLocale)

    when {
        callendar.get(Calendar.DAY_OF_YEAR) == currentDayOfYear &&
                callendar.get(Calendar.YEAR) == currentYear ->
            simpleDateFormat = SimpleDateFormat("сегодня в H:mm", currentLocale)
//            simpleDateFormat.applyLocalizedPattern("today 'in' H:mm")
        callendar.get(Calendar.YEAR) == currentYear ->
            simpleDateFormat = SimpleDateFormat("d MMM в H:mm", currentLocale)
//            simpleDateFormat.applyLocalizedPattern("d MMM 'in' H:mm")
    }

    return simpleDateFormat.format(date)
}


fun getFriendlyDateString(context: Context, utcDateInMillis: Long): String {
    val calendar = Calendar.getInstance()


    val localDate = getNormalizedUtcMsForToday(calendar, utcDateInMillis)

    val daysFromEpochToProvidedDate = elapsedDaysSinceEpoch(calendar, localDate)

    val daysFromEpochToToday = elapsedDaysSinceEpoch(calendar, System.currentTimeMillis())

    return if (daysFromEpochToProvidedDate == daysFromEpochToToday) {

        getDayName(context, calendar, localDate)
    } else {
        val flags = (DateUtils.FORMAT_SHOW_DATE
                or DateUtils.FORMAT_NO_YEAR
                or DateUtils.FORMAT_ABBREV_ALL
                or DateUtils.FORMAT_SHOW_WEEKDAY)

        DateUtils.formatDateTime(context, localDate, flags)
    }
}


fun getNormalizedUtcMsForToday(calendar: Calendar, utcDate: Long): Long {
    /*
         * This TimeZone represents the device's current time zone. It provides us with a means
         * of acquiring the offset for local time from a UTC time stamp.
         */
    calendar.timeInMillis = utcDate * 1000

    val currentTimeZone = calendar.timeZone
    /*
         * The getOffset method returns the number of milliseconds to add to UTC time to get the
         * elapsed time since the epoch for our current time zone. We pass the current UTC time
         * into this method so it can determine changes to account for daylight savings time.
         */
    var gmtOffsetMillis = currentTimeZone.rawOffset

    if (currentTimeZone.inDaylightTime(calendar.time)) {
        gmtOffsetMillis += currentTimeZone.dstSavings
        calendar.add(Calendar.MILLISECOND, Calendar.DST_OFFSET)
    } else {
        calendar.add(Calendar.MILLISECOND, Calendar.DST_OFFSET)
    }
    /*
         * UTC time is measured in milliseconds from January 1, 1970 at midnight from the GMT
         * time zone. Depending on your time zone, the time since January 1, 1970 at midnight (GMT)
         * will be greater or smaller. This variable represents the number of milliseconds since
         * January 1, 1970 (GMT) time.
         */
    return calendar.timeInMillis
}

private fun getDayName(context: Context, calendar: Calendar, dateInMillis: Long): String {
    calendar.clear()
    calendar.timeInMillis = dateInMillis

    val currentCalendar = Calendar.getInstance()
    currentCalendar.timeInMillis = System.currentTimeMillis()

    val daysFromEpochToProvidedDateSeconds = calendar.timeInMillis / 1000
    val daysFromEpochToTodaySeconds = currentCalendar.timeInMillis / 1000

    val diffMilliseconds = currentCalendar.timeInMillis - calendar.timeInMillis
    val diffSec = (daysFromEpochToTodaySeconds - daysFromEpochToProvidedDateSeconds)
    val diffHours = diffSec % SECONDS_IN_DAY / SECONDS_IN_HOUR
    val diffMinutes = diffSec % SECONDS_IN_HOUR / SECONDS_IN_MINUTE


    return when (diffMinutes) {
        0L -> context.getString(R.string.justNow)
        1L -> context.getString(R.string.one_minutes_ago)
        2L -> context.getString(R.string.two_minutes_ago)
        3L -> context.getString(R.string.number_of_minutes)
        in 3L..60L -> context.resources.getQuantityString(R.plurals.time_plurals, diffMinutes.toInt(), diffMinutes.toInt())
        60L -> context.getString(R.string.hour_ago)
        120L -> context.getString(R.string.two_hour_ago)
        180L -> context.getString(R.string.three_hour_ago)
        in 180L..1440 -> context.getString(R.string.up_to_day, diffMilliseconds)
        in 1440L..2880 -> context.getString(R.string.yesterday, diffMilliseconds)
        else -> context.getString(R.string.fullDate, diffMilliseconds, diffMilliseconds, diffMilliseconds, diffMilliseconds)
    }
}

private fun getReadableDateString(context: Context, timeInMillis: Long): String {
    val flags = (DateUtils.FORMAT_SHOW_DATE
            or DateUtils.FORMAT_NO_YEAR
            or DateUtils.FORMAT_SHOW_WEEKDAY)

    return DateUtils.formatDateTime(context, timeInMillis, flags)
}

private fun elapsedDaysSinceEpoch(calendar: Calendar, dateInMillis: Long): Long {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    calendar.clear()
    calendar.timeInMillis = dateInMillis

    return if (calendar.get(Calendar.YEAR) == currentYear) {
        return TimeUnit.MILLISECONDS.toDays(dateInMillis)
    } else TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis())
}

fun getTypeFace(context: Context, path: String) {
    Typeface.createFromAsset(context.assets, path)
}


//Преобразовывает длительность медиафайлов
fun getReadableDurationString(durationSeconds: Long): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = durationSeconds * 1000
    val timeFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
    return timeFormat.format(calendar.time)
}


//Количество просмотрров видеофайлов
fun formatViewsCount(viewsCount: Int): String {
    val formatter: DecimalFormat = NumberFormat.getInstance(Locale.US) as DecimalFormat
    val symbols = formatter.decimalFormatSymbols

    symbols.groupingSeparator = ' '
    formatter.decimalFormatSymbols = symbols

    return formatter.format(viewsCount)
}

fun formatSize(bytes: Long): String {
    val unit = 1000
    if (bytes < unit) return bytes.toString() + " B"
    val exp = (Math.log(bytes.toDouble()) / Math.log(unit.toDouble())).toInt()
    val pre = "kMGTPE"[exp - 1].toString()

    return String.format("%.1f %sB", bytes / Math.pow(unit.toDouble(), exp.toDouble()), pre)
}


fun openUrlInActionView(url: String, context: Context) {
    if (url.isNotEmpty()) {
        val uri: Uri = Uri.parse(url)
        val intentView = Intent(Intent.ACTION_VIEW, uri)
        if (intentView.resolveActivity(context.packageManager) != null) {
            context.startActivity(intentView)
        }
    }
}


fun removeExtFromText(fileName: String): String {

    for ((index, char) in fileName.withIndex()) {
        if (char.toString() == ".") {
            fileName.removeRange(index..fileName.lastIndex)
        }
    }
    return fileName
}

fun splitStringForIdAndOwnerId(str: String): List<String> {
    val regex = Regex("[^-?0-9]")

    return str.replace(regex, " ")
            .trim()
            .split(" ")
}





















