package ua.rash1k.vkgroups.models.view

import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.item_topic.view.*
import ua.rash1k.vkgroups.models.Topic
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder


class TopicViewModel(topic: Topic,
                     var mTitle: String = topic.title,
                     var mCommentsCount: String = "${topic.comments} messages") : BaseViewModel() {


    override fun getTypeIdLayout(): LayoutTypes {
        return LayoutTypes.Topic
    }

    override fun onCreateViewHolder(view: View): BaseViewHolder<BaseViewModel> {
        return TopicViewHolder(view) as BaseViewHolder<BaseViewModel>
    }


    class TopicViewHolder(itemView: View) : BaseViewHolder<TopicViewModel>(itemView) {

        private val mTitle: TextView = itemView.tv_title
        private val mCommentsCount: TextView = itemView.tv_comments_count


        override fun bindViewHolder(itemModel: TopicViewModel) {
            mTitle.text = itemModel.mTitle
            mCommentsCount.text = itemModel.mCommentsCount
        }

        override fun unbindViewHolder() {
            mTitle.text = null
            mCommentsCount.text = null
        }
    }
}

