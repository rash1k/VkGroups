package ua.rash1k.vkgroups.models.view


import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.item_comment_body.view.*
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.common.manager.MyFragmentManager
import ua.rash1k.vkgroups.common.utils.UiHelper
import ua.rash1k.vkgroups.models.CommentItem
import ua.rash1k.vkgroups.ui.activity.BaseActivity
import ua.rash1k.vkgroups.ui.fragment.OpenedCommentFragment
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder
import javax.inject.Inject


class CommentBodyViewModel(commentItem: CommentItem) : BaseViewModel() {

    val id: Int = commentItem.id

    val text: String = commentItem.text

    val attachmentsString: String = commentItem.attachmentsString


    override fun isItemDecoration(): Boolean {
        return true
    }

    override fun getTypeIdLayout(): LayoutTypes {
        return LayoutTypes.CommentBody
    }

    override fun onCreateViewHolder(view: View): BaseViewHolder<BaseViewModel> {
        return CommentBodyViewHolder(view) as BaseViewHolder<BaseViewModel>
    }


    class CommentBodyViewHolder(itemView: View) : BaseViewHolder<CommentBodyViewModel>(itemView) {

        @Inject
        lateinit var mGoogleFont: Typeface

        @Inject
        lateinit var uiHelper: UiHelper

        @Inject
        lateinit var mFragmentManager: MyFragmentManager

        var tvText: TextView = itemView.tv_text

        var tvAttachments: TextView = itemView.tv_attachments

        init {
            MyApplication.sApplicationComponent.inject(this)
            tvAttachments.typeface = mGoogleFont
        }

        override fun bindViewHolder(itemModel: CommentBodyViewModel) {
            uiHelper.setUpTextViewWithMessage(tvText, itemModel.text)
            uiHelper.setUpTextViewWithVisibility(tvAttachments, itemModel.attachmentsString)

            itemView.setOnClickListener {
                mFragmentManager.addFragment(itemView.context as BaseActivity,
                        OpenedCommentFragment.newInstance(itemModel.id), R.id.main_wrapper)
            }
        }

        override fun unbindViewHolder() {
            tvText.text = null
            tvAttachments.text = null
        }
    }
}