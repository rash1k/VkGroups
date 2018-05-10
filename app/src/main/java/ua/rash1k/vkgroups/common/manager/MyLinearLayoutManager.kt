package ua.rash1k.vkgroups.common.manager

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet

//Этот класс имеет доступ к View списка и будет проверять находится ли список в той позиции
//в которой нам нужно подгружать следущие элементы

//Для того чтобы подгружать новые элементы нам нужно знать количество элементов в адаптере
//Так как мы желим каждый элемент на 3, количество будет отличаться от реального
class MyLinearLayoutManager : LinearLayoutManager {

    constructor(context: Context) : super(context)


    constructor(context: Context, orientation: Int,
                reverseLayout: Boolean) : super(context, orientation, reverseLayout)


    constructor(context: Context, attributeSet: AttributeSet,
                defStyleAttr: Int, defStyleRes: Int) : this(context)


    fun isOnNextPagePosition(): Boolean {
        val visibleItemCount = childCount
        val totalItemCount = itemCount
        val pastVisibleItems = findFirstVisibleItemPosition()

        return (visibleItemCount + pastVisibleItems) >= totalItemCount / 2
    }
}