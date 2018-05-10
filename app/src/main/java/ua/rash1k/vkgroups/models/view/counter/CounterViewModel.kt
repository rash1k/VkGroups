package ua.rash1k.vkgroups.models.view.counter

import ua.rash1k.vkgroups.R


abstract class CounterViewModel(count: Int) {

    var mCount: Int = count
    var mIconColor = R.color.colorIconDis
    var mTextColor = R.color.colorIconDis


    init {
        if (count > 0) {
            setDefaultColor()
        } else {
            setDisableColor()
        }
    }

    private fun setDefaultColor() {
        mIconColor = R.color.colorIcon
        mTextColor = R.color.colorIcon
    }

    private fun setDisableColor() {
        mIconColor = R.color.colorIconDis
        mTextColor = R.color.colorIconDis
    }

    protected fun setAccentColor() {
        mIconColor = R.color.colorAccent
        mTextColor = R.color.colorAccent
    }
}