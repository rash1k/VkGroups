package ua.rash1k.vkgroups.models.view.info

import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.item_info_status.view.*
import ua.rash1k.vkgroups.models.Group
import ua.rash1k.vkgroups.models.view.BaseViewModel
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder


class InfoStatusViewModel(group: Group,
                          val mStatus: String = group.status,
                          val mDescription: String = group.description,
                          val mSite: String = group.site) : BaseViewModel() {


    override fun getTypeIdLayout(): LayoutTypes {
        return LayoutTypes.InfoStatus
    }

    override fun onCreateViewHolder(view: View): BaseViewHolder<BaseViewModel> {
        return InfoStatusViewHolder(view) as BaseViewHolder<BaseViewModel>
    }


    class InfoStatusViewHolder(view: View) : BaseViewHolder<InfoStatusViewModel>(view) {

        private var mTextViewStatus: TextView = view.tv_status_text
        private var mTextViewDescription: TextView = view.tv_description_text
        private var mTextViewSite: TextView = view.tv_site_text


        override fun bindViewHolder(itemModel: InfoStatusViewModel) {
            mTextViewStatus.text = itemModel.mStatus
            mTextViewDescription.text = itemModel.mDescription
            mTextViewSite.text = itemModel.mSite
        }

        override fun unbindViewHolder() {
            mTextViewStatus.text = null
            mTextViewDescription.text = null
            mTextViewSite.text = null
        }
    }
}