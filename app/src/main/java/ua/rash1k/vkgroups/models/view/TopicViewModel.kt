package ua.rash1k.vkgroups.models.view

import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.item_topic.view.*
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.common.manager.MyFragmentManager
import ua.rash1k.vkgroups.models.Place
import ua.rash1k.vkgroups.models.Topic
import ua.rash1k.vkgroups.ui.activity.BaseActivity
import ua.rash1k.vkgroups.ui.fragment.TopicCommentsFragment
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder
import javax.inject.Inject


class TopicViewModel(topic: Topic,
                     var mTitle: String = topic.title,
                     var mCommentsCount: String = "${topic.comments} messages") : BaseViewModel() {


    var id: Int = topic.id
    var groupId: Int = topic.groupId


    override fun getTypeIdLayout(): LayoutTypes {
        return LayoutTypes.Topic
    }

    override fun onCreateViewHolder(view: View): BaseViewHolder<BaseViewModel> {
        return TopicViewHolder(view) as BaseViewHolder<BaseViewModel>
    }


    class TopicViewHolder(itemView: View) : BaseViewHolder<TopicViewModel>(itemView) {

        private val mTitle: TextView = itemView.tv_title
        private val mCommentsCount: TextView = itemView.tv_comments_count

        @Inject
        lateinit var mMyFragmentManager: MyFragmentManager

        init {
            MyApplication.sApplicationComponent.inject(this)
        }


        override fun bindViewHolder(itemModel: TopicViewModel) {
            mTitle.text = itemModel.mTitle
            mCommentsCount.text = itemModel.mCommentsCount

            itemView.setOnClickListener {
                mMyFragmentManager.addFragment(itemView.context as BaseActivity?,
                        TopicCommentsFragment.newInstance(Place(itemModel.groupId.toString(),
                                itemModel.id.toString())), R.id.main_wrapper)
            }
        }

        override fun unbindViewHolder() {
            mTitle.text = null
            mCommentsCount.text = null
        }
    }
}

