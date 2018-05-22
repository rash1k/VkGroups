package ua.rash1k.vkgroups.models.view

import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_groups.view.*
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.models.Group
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder


class GroupsViewModel(group: Group,
                      val id: Int = group.id,
                      val mProfileImage: String = group.photo100,
                      val groupName: String = group.name,
                      val subscribeCount: Int = group.subscribeCount) : BaseViewModel() {


    override fun getTypeIdLayout(): LayoutTypes {
        return LayoutTypes.Groups
    }

    override fun onCreateViewHolder(view: View): BaseViewHolder<BaseViewModel> {
        return GroupsViewHolder(view) as BaseViewHolder<BaseViewModel>
    }

    class GroupsViewHolder(view: View) : BaseViewHolder<GroupsViewModel>(view) {

        val mCvProfileImage: CircleImageView = view.civ_profile_image
        val mTvGroupName: TextView = view.tv_group_name
        val mSusbscribeCount: TextView = view.tv_subscribe_count


        override fun bindViewHolder(itemModel: GroupsViewModel) {
            Glide.with(itemView.context).load(itemModel.mProfileImage).into(mCvProfileImage)

            mTvGroupName.text = itemModel.groupName
            mSusbscribeCount.text = String.format(itemView.context.getString(R.string.count_subscribe),
                    itemModel.subscribeCount)


            itemView.setOnClickListener { }

        }

        override fun unbindViewHolder() {
            mCvProfileImage.setImageBitmap(null)
            mTvGroupName.text = null
            mSusbscribeCount.text = null
        }

    }
}