package ua.rash1k.vkgroups.models.view.info

import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.item_info_links.view.*
import ua.rash1k.vkgroups.models.view.BaseViewModel
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder


class InfoLinksViewModel : BaseViewModel() {
    override fun getTypeIdLayout(): LayoutTypes {
        return LayoutTypes.InfoLinks
    }

    override fun onCreateViewHolder(view: View): BaseViewHolder<BaseViewModel> {
        return InfoLinksViewHolder(view) as BaseViewHolder<BaseViewModel>
    }


    class InfoLinksViewHolder(view: View) : BaseViewHolder<InfoLinksViewModel>(view) {

        private var mTextViewLinks: TextView = view.tv_links

        override fun bindViewHolder(itemModel: InfoLinksViewModel) {
        }

        override fun unbindViewHolder() {
        }
    }
}