package ua.rash1k.vkgroups.common.utils

import android.view.View
import android.widget.TextView
import ua.rash1k.vkgroups.R


class UiHelper {

    fun setUpTextViewWithVisibility(textView: TextView, str: String) {
        if (str.isNotEmpty()) {
            textView.visibility = View.VISIBLE
        } else {
            textView.visibility = View.GONE
        }
        textView.text = str
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