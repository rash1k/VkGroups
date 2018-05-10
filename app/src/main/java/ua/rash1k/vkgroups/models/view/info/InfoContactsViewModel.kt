package ua.rash1k.vkgroups.models.view.info

import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.item_info_contacts.view.*
import ua.rash1k.vkgroups.models.view.BaseViewModel
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder


class InfoContactsViewModel : BaseViewModel() {

    override fun getTypeIdLayout(): LayoutTypes {
        return LayoutTypes.InfoContacts
    }

    override fun onCreateViewHolder(view: View): BaseViewHolder<BaseViewModel> {
        return InfoContactsViewHolder(view) as BaseViewHolder<BaseViewModel>
    }


    class InfoContactsViewHolder(view: View) : BaseViewHolder<InfoContactsViewModel>(view) {

        private val mTextViewContacts: TextView = view.tv_contacts


        override fun bindViewHolder(itemModel: InfoContactsViewModel) {
        }

        override fun unbindViewHolder() {
        }
    }
}