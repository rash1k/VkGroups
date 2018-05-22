package ua.rash1k.vkgroups.models.view

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder


//Мы получаем ответ от сервера и парсим его в POJO
//На уровне макета нам нужны не все данные, а некоторые данные нуждаются в обработке
//Чтобы не тратить ресурсы зря и не перегружать OnBindView обработкой и преобразованием данных
//"Этот класс ответственный за преобразование данных и создание нужных ViewHolder
abstract class BaseViewModel {

    //Чтобы различать макеты данного типа и для Inflate
    //Конкретный дочерний класс будет возвращать свой тип
    abstract fun getTypeIdLayout(): LayoutTypes

    //Нужен для того чтобы переложить ответсвенность за создание
    //конкретного ViewHolder на дочерние классы
    fun createViewHolder(parent: ViewGroup): BaseViewHolder<BaseViewModel> {
        val view = LayoutInflater.from(parent.context)
                .inflate(getTypeIdLayout().idLayout, parent, false)

        return onCreateViewHolder(view)
    }

    protected abstract fun onCreateViewHolder(view: View): BaseViewHolder<BaseViewModel>

    //Нужен для содержания типа Layout и ссылки на него
    enum class LayoutTypes(@LayoutRes val idLayout: Int) {
        NewsFeedHeader(R.layout.item_news_header),
        NewsFeedBody(R.layout.item_news_body),
        NewsFeedFooter(R.layout.item_news_footer),

        Member(R.layout.item_member),
        Topic(R.layout.item_topic),
        NewsFeed(R.layout.item_news_feed),
        Groups(R.layout.item_groups),

        InfoStatus(R.layout.item_info_status),
        InfoContacts(R.layout.item_info_contacts),
        InfoLinks(R.layout.item_info_links),

        AttachmentAudio(R.layout.item_attachment_audio),
        AttachmentDoc(R.layout.item_attachment_doc),
        AttachmentDocImage(R.layout.item_attachment_doc_image),
        AttachmentImage(R.layout.item_attachment_image),
        AttachmentLink(R.layout.item_attachment_link),
        AttachmentLinkExternal(R.layout.item_attachment_link_external),
        AttachmentPage(R.layout.item_attachment_page),
        AttachmentVideo(R.layout.item_attachment_video),

        OpenedPostHeader(R.layout.item_opened_post_header),
        OpenedPostRepostHeader(R.layout.item_opened_post_repost_header),

        CommentHeader(R.layout.item_comment_header),
        CommentBody(R.layout.item_comment_body),
        CommentFooter(R.layout.item_comment_footer),

        CreatePostText(R.layout.item_create_post_text);
    }

    open fun isItemDecoration(): Boolean = false
}