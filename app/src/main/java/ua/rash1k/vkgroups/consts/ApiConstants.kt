package ua.rash1k.vkgroups.consts

import com.vk.sdk.VKScope


object ApiConstants {

//У всех наших запросов к VK API будут общие поля — access token и version,
// а так же иногда будет применяться форматирование входных параметров.

//Поэтому для удобства будем создавать модели запроса.


//Use for resolve API VK


    //Позволит запрашивать email пользователя для отображения в Drawer
    val DEFAULT_LOGIN_SCOPE: Array<String> = arrayOf(VKScope.EMAIL, VKScope.FRIENDS, VKScope.WALL,
            VKScope.DIRECT, VKScope.AUDIO, VKScope.VIDEO, VKScope.PAGES, VKScope.GROUPS, VKScope.MESSAGES,
            VKScope.PHOTOS, VKScope.STATS, VKScope.DOCS)

    const val NEED_LIKES = "need_likes"
    const val TOPIC_ID = "topic_id"
    const val GROUP_ID = "group_id"

    const val DEFAULT_VERSION = 5.74
    const val DEFAULT_COUNT = 10

    const val VIDEOS = "videos"

    //Для аватарки пользователя
    const val DEFAULT_USERS_FIELDS = "photo_100"

    //Для получения участников групп
    //Cписок дополнительных полей, которые необходимо вернуть
    const val DEFAULT_MEMBERS_FIELD = "name,photo_100"
    const val DEFAULT_GROUP_FIELDS = "status,description,site,links,contacts"
    const val DEFAULT_WALL_BY_ID_FIELDS = "status,description,site,links,contacts"


    const val MY_GROUP_ID = -86529522
    //    const val MY_GROUP_ID = 458364591
    const val LIS_HIM_GROUP_NAME = "lishimstroy"


    //Для получения ленты новостей
//    const val NEWS_FILTERS = "post,video,photo,wall_photo"
    const val NEWS_FILTERS = "post"

}