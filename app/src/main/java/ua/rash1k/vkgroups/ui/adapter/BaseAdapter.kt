package ua.rash1k.vkgroups.ui.adapter

import android.support.v4.util.ArrayMap
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import ua.rash1k.vkgroups.models.view.BaseViewModel
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder


class BaseAdapter : RecyclerView.Adapter<BaseViewHolder<BaseViewModel>>() {

    //Содержит все добавленные в адаптер элементы
    private var list: MutableList<BaseViewModel> = mutableListOf()

    //Переменная для хранения типа модели и макета
    //Key тип макета Layout
    //Value - сама модель
    //Нужен для создания ViewHolder конкретного типа
    private var mTypeInstances = ArrayMap<Int, BaseViewModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BaseViewModel> {
        val viewModel: BaseViewModel = mTypeInstances[viewType]!!
        return viewModel.createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<BaseViewModel>, position: Int) {
        val itemModel: BaseViewModel = getItemByPosition(position)
        holder.bindViewHolder(itemModel)
    }

    //Очищаем данные из ViewHolder
    override fun onViewRecycled(holder: BaseViewHolder<BaseViewModel>) {
        super.onViewRecycled(holder)
        holder.unbindViewHolder()
    }

    //Возвращает тип View item
    override fun getItemViewType(position: Int): Int {
        return getItemByPosition(position).getTypeIdLayout().idLayout
    }


    override fun getItemCount(): Int {
        return list.size
    }

    private fun registerTypeInstance(item: BaseViewModel) {
        val key = item.getTypeIdLayout().idLayout
        if (key !in mTypeInstances) {
            mTypeInstances[key] = item
        }
    }

    //Возвращает item BaseViewModel by position
    private fun getItemByPosition(position: Int): BaseViewModel = list[position]

    internal fun addItems(newItems: List<BaseViewModel>) {
        newItems.forEach { registerTypeInstance(it) }

        list.addAll(newItems)

        notifyDataSetChanged()
    }

    internal fun insertItem(newItems: BaseViewModel) {
        registerTypeInstance(newItems)
        list.add(newItems)

        notifyItemInserted(itemCount - 1)
    }

    //Метод для замены элементов в списке
    fun setItems(newsItems: List<BaseViewModel>) {
        list.clear()
        addItems(newsItems)
    }


    //Будет переберать все элементы списка
    //проверять является ли элемент реальным и возвращать реальное кол. элементов
    fun getRealItemCount(): Int {
        var count = 0
//        var item = 0
        for (position in 0 until itemCount) {
            if (!getItemByPosition(position).isItemDecoration()) {
                count += 1
            }
        }

        /* while (item < itemCount) {
             if (!getItemByPosition(item).isItemDecoration()) {
                 count += 1
             }
             ++item
         }*/

        return count
    }
}