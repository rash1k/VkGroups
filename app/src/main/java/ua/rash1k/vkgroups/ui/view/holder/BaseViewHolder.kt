package ua.rash1k.vkgroups.ui.view.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import ua.rash1k.vkgroups.models.view.BaseViewModel

//Параметр нужнен для безопасности типа чтобы работать только с BaseViewModel
abstract class BaseViewHolder<ItemModel:BaseViewModel>(itemView: View) : RecyclerView.ViewHolder(itemView) {


    //Используется для заполнения макета данными модели ItemModel
    abstract fun bindViewHolder(itemModel: ItemModel)

    //Для разгрузки макета когда он не используется
    abstract fun unbindViewHolder()
}