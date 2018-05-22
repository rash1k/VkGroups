package ua.rash1k.vkgroups.common.utils

import com.vk.sdk.api.VKResponse
import com.vk.sdk.api.model.VKAttachments
import org.json.JSONArray
import ua.rash1k.vkgroups.models.*
import ua.rash1k.vkgroups.models.attachment.ApiAttachment
import ua.rash1k.vkgroups.models.view.BaseViewModel
import ua.rash1k.vkgroups.models.view.attachment.*
import ua.rash1k.vkgroups.rest.model.response.ItemWithSendersResponse
import java.util.ArrayList
import kotlin.NoSuchElementException

const val TAG = "VkListHelper"
//Будет заполнять поля senderName и senderPhoto для отправителя записи,
// а также для отправителя репоста:

fun getWallList(response: ItemWithSendersResponse<WallItem>): List<WallItem> {
    val wallItems = response.items

    for (wallItem in wallItems) {
        val sender = response.getSender(wallItem.fromId)

        if (sender != null) {
            wallItem.senderName = sender.getFullName()

            val photo = sender.getPhoto()
            if (photo != null) {
                wallItem.senderPhoto = photo
            }
        }

        wallItem.attachmentsString = convertAttachmentsToFontIcons(wallItem.attachments)

        val haveSharedRepost = wallItem.haveSharedRepost()
        val wallItemRepost = wallItem.getSharedRepost()

        if (haveSharedRepost && wallItemRepost != null) {
            val repostsSender = response.getSender(wallItemRepost.fromId)
            wallItemRepost.senderName = repostsSender!!.getFullName()
            wallItemRepost.senderPhoto = repostsSender.getPhoto()!!

            wallItemRepost.attachmentsString = convertAttachmentsToFontIcons(wallItemRepost.attachments)
        }
    }
    return wallItems
}


fun getNewsList(response: Response): List<News> {
    val newsItems = response.items

    newsItems.onEach { news ->

        val sender = response.getSender(news.sourceId, news.type)
        news.senderName = sender.getFullName()
        news.senderPhoto = sender.getPhoto()


        news.signerName = response.getSignedFullName(news.signerId, news.type)


        val haveSharedRepost = news.haveSharedRepost()
        val newsItemRepost = news.getSharedRepost()

        if (haveSharedRepost != null && haveSharedRepost && newsItemRepost != null) {
            val repostsSender = response.getSender(news.sourceId, news.type)

            newsItemRepost.senderName = repostsSender.getFullName()
            newsItemRepost.senderPhoto = repostsSender.getPhoto()

            newsItemRepost.signerName = response.getSignedFullName(newsItemRepost.signerId, newsItemRepost.type)
        }

    }
    return newsItems
}


fun getAttachmentViewModelItems(attachments: List<ApiAttachment>): List<BaseViewModel> {

    val attachmentsViewModelItems = arrayListOf<BaseViewModel>()

    for (attachment in attachments) {
        when (attachment.type) {

            VKAttachments.TYPE_PHOTO -> {
                if (attachment.photo != null) {
                    attachmentsViewModelItems.add(ImageAttachmentViewModel(attachment.photo!!))
                }
            }

            VKAttachments.TYPE_AUDIO -> {
                if (attachment.audio != null) {
                    attachmentsViewModelItems.add(AudioAttachmentViewModel(attachment.audio!!))
                }
            }
            VKAttachments.TYPE_DOC -> {
                if (attachment.doc != null && attachment.doc?.preview != null) {
                    attachmentsViewModelItems.add(DocImageAttachmentViewModel(attachment.doc!!))
                } else {
                    attachmentsViewModelItems.add(DocAttachmentViewModel(attachment.doc!!))
                }
            }
            VKAttachments.TYPE_LINK -> {
                if (attachment.link != null && attachment.link?.isExternal == 1) {
                    attachmentsViewModelItems.add(LinkExternalViewModel(attachment.link!!))
                } else {
                    attachmentsViewModelItems.add(LinkAttachmentViewModel(attachment.link!!))
                }
            }

            VKAttachments.TYPE_WIKI_PAGE -> {
                if (attachment.page != null)
                    attachmentsViewModelItems.add(PageAttachmentViewModel(attachment.page!!))
            }

            VKAttachments.TYPE_VIDEO -> {
                if (attachment.video != null) {
                    attachmentsViewModelItems.add(VideoAttachmentViewModel(attachment.video!!))
                }
            }

            else -> throw NoSuchElementException("Attachment type ${attachment.type} is not supported")
        }
    }

    return attachmentsViewModelItems
}


fun getCommentList(response: ItemWithSendersResponse<CommentItem>, isFromTopic: Boolean = false)
        : List<CommentItem> {
    val commentItems = response.items

    for (commentItem in commentItems) {
        val owner = response.getSender(commentItem.fromId)
        commentItem.senderName = owner?.getFullName() ?: ""
        commentItem.senderPhoto = owner?.getPhoto() ?: ""
        commentItem.attachmentsString = convertAttachmentsToFontIcons(commentItem.attachments)
        commentItem.isFromTopic = isFromTopic
    }
    return commentItems
}


fun getGroupsList(response: VKResponse): List<Group> {

    val responseObject = response.json.getJSONObject("response")
    val count = responseObject.getInt("count")
    val listGroup = ArrayList<Group>(count)

    val items: JSONArray = responseObject.getJSONArray("items")

    var group: Group

    var id: Int
    var name: String
    var screenName: String
    var photo100: String

    for (i in 0 until items.length()) {
        group = Group()
        val obj = items.getJSONObject(i)
        id = obj.getInt("id")
        name = obj.getString("name")
        screenName = obj.getString("screen_name")
        photo100 = obj.getString("photo_100")

        group.id = id
        group.name = name
        group.screenName = screenName
        group.photo100 = photo100

        listGroup.add(group)
    }

    return listGroup
}










