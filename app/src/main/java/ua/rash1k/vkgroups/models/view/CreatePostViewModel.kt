package ua.rash1k.vkgroups.models.view

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.item_create_post_text.view.*
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder


class CreatePostViewModel : BaseViewModel() {

    var mMessage: String = ""


    override fun getTypeIdLayout(): LayoutTypes {
        return LayoutTypes.CreatePostText
    }

    override fun onCreateViewHolder(view: View): BaseViewHolder<BaseViewModel> {
        return NewsPostViewHolder(view) as BaseViewHolder<BaseViewModel>
    }


    class NewsPostViewHolder(view: View) : BaseViewHolder<CreatePostViewModel>(view) {


        val mEtMessage: EditText = view.et_message


        override fun bindViewHolder(itemModel: CreatePostViewModel) {
            mEtMessage.setText(itemModel.mMessage)

            mEtMessage.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s != null && s.isNotEmpty()) {
                        itemModel.mMessage = s.toString()
                    }
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
        }

        override fun unbindViewHolder() {
            mEtMessage.text = null
        }

    }
}