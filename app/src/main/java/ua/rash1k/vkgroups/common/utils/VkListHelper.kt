package ua.rash1k.vkgroups.common.utils

import com.vk.sdk.api.model.VKAttachments
import ua.rash1k.vkgroups.models.CommentItem
import ua.rash1k.vkgroups.models.News
import ua.rash1k.vkgroups.models.Response
import ua.rash1k.vkgroups.models.WallItem
import ua.rash1k.vkgroups.models.attachment.ApiAttachment
import ua.rash1k.vkgroups.models.view.BaseViewModel
import ua.rash1k.vkgroups.models.view.attachment.*
import ua.rash1k.vkgroups.rest.model.response.ItemWithSendersResponse

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

//        news.contentPhoto = news.getPhoto()

        news.signerName = response.getSignedFullName(news.signerId, news.type)

//        news.attachmentsString = convertAttachmentsToFontIcons(news.attachments)

        val haveSharedRepost = news.haveSharedRepost()
        val newsItemRepost = news.getSharedRepost()

        if (haveSharedRepost != null && haveSharedRepost && newsItemRepost != null) {
            val repostsSender = response.getSender(news.sourceId, news.type)

            newsItemRepost.senderName = repostsSender.getFullName()
            newsItemRepost.senderPhoto = repostsSender.getPhoto()

            newsItemRepost.signerName = response.getSignedFullName(newsItemRepost.signerId, newsItemRepost.type)
//            newsItemRepost.attachmentsString = convertAttachmentsToFontIcons(newsItemRepost.attachments)
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














