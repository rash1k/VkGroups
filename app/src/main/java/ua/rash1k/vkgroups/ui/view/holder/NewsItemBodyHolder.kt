package ua.rash1k.vkgroups.ui.view.holder

import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.item_news_body.view.*
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.common.manager.MyFragmentManager
import ua.rash1k.vkgroups.common.utils.UiHelper
import ua.rash1k.vkgroups.models.view.NewsItemBodyViewModel
import ua.rash1k.vkgroups.ui.activity.BaseActivity
import ua.rash1k.vkgroups.ui.fragment.OpenedPostFragment
import javax.inject.Inject


class NewsItemBodyHolder(itemView: View) : BaseViewHolder<NewsItemBodyViewModel>(itemView) {


    private val mTextViewBody: TextView = itemView.tv_body
    private val mTextViewAttachments: TextView = itemView.tv_attachments
    @Inject
    lateinit var mFontGoogle: Typeface

    @Inject
    lateinit var myFragmentManager: MyFragmentManager

    @Inject
    lateinit var uiHelper: UiHelper

    init {
        MyApplication.sApplicationComponent.inject(this)

        mTextViewAttachments.typeface = mFontGoogle
    }

    override fun bindViewHolder(itemModel: NewsItemBodyViewModel) {

        uiHelper.setUpTextViewWithVisibility(mTextViewBody, itemModel.text)
        uiHelper.setUpTextViewWithVisibility(mTextViewAttachments, itemModel.text)

        mTextViewBody.text = itemModel.text
        mTextViewAttachments.text = itemModel.mAttachmentString


        itemView.setOnClickListener {
            myFragmentManager.addFragment(it.context as BaseActivity,
                    OpenedPostFragment.newInstance(itemModel.id), R.id.main_wrapper)
        }

    }

    override fun unbindViewHolder() {
        mTextViewBody.text = null
        mTextViewAttachments.text = null
        itemView.setOnClickListener(null)
    }
}