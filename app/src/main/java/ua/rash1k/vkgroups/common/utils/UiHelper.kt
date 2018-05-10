package ua.rash1k.vkgroups.common.utils

import android.view.View
import android.widget.TextView
import ua.rash1k.vkgroups.R


class UiHelper {

    fun setUpTextViewWithVisibility(textView: TextView, str: String) {
        textView.text = str
        if (str.isEmpty()) {
//            textView.text = str
            textView.visibility = View.GONE
        } else {
//            textView.text = str
            textView.visibility = View.VISIBLE

        }
    }

    fun setUpTextViewWithMessage(textView: TextView, message: String) {
        val s: String
        val color: Int

        if (!message.isEmpty()) {
            textView.visibility = View.VISIBLE
            color = android.R.color.primary_text_light
            s = message
        } else {
            s = "Поделился"
            color = R.color.colorIcon
        }

        textView.text = s
        textView.setTextColor(textView.resources.getColor(color))
    }
}