package ua.rash1k.vkgroups.consts

import com.vk.sdk.VKScope
import org.json.JSONException
import org.json.JSONObject


object ApiConstants {

//У всех наших запросов к VK API будут общие поля — access token и version,
// а так же иногда будет применяться форматирование входных параметров.

//Поэтому для удобства будем создавать модели запроса.


//Use for resolve API VK


    //Список разрешений, который позволит в том числе запрашивать email пользователя для отображения в Drawer
    val DEFAULT_LOGIN_SCOPE: Array<String> = arrayOf(VKScope.EMAIL, VKScope.FRIENDS, VKScope.WALL,
            VKScope.DIRECT, VKScope.AUDIO, VKScope.VIDEO, VKScope.PAGES, VKScope.GROUPS, VKScope.MESSAGES,
            VKScope.PHOTOS, VKScope.STATS, VKScope.DOCS)

    //For board.getComments methods
    const val NEED_LIKES = "need_likes"
    const val TOPIC_ID = "topic_id"
    const val GROUP_ID = "group_id"

    const val DEFAULT_VERSION = 5.74
    const val DEFAULT_COUNT = 10

    const val VIDEOS = "videos"

    //For avatars users
    const val DEFAULT_USERS_FIELDS = "photo_100"

    //To obtain group members
    //List additional  fields, which must be returned
    const val DEFAULT_MEMBERS_FIELD = "name,photo_100"
    const val DEFAULT_GROUP_FIELDS = "status,description,site,links,contacts"
    const val DEFAULT_WALL_BY_ID_FIELDS = "status,description,site,links,contacts"


    const val MY_GROUP_ID = -86529522
    const val DEBUG_GROUP_ID = -12522738
    const val LIS_HIM_GROUP_NAME = "lishimstroy"


    //Для получения списка групп
    const val APP_ID = "app_id"

    //Для получения ленты новостей
//    const val NEWS_FILTERS = "post,video,photoUrl,wall_photo"
    const val NEWS_FILTERS = "post"

    //FCM Constant
    const val TOKEN = "token"
    const val SYSTEM_VERSION = "system_version"
    const val DEVICE_MODEL = "device_model"
    const val DEVICE_ID = "device_id"
    const val SETTINGS = "settings"

    fun getDefaultPushSettings(): JSONObject {
        try {
            return JSONObject("{\"comment\":\"on\", \"reply\":\"on\", \"new_post\":\"on\"}")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        throw JSONException("Invalid Settings")
    }

}