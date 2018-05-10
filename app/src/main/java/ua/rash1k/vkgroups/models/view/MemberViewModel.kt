package ua.rash1k.vkgroups.models.view

import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_member.view.*
import ua.rash1k.vkgroups.models.Member
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder


class MemberViewModel(member: Member,
                      private var id: Int = member.id,
                      private var group_id: Int = member.group_id,
                      private var photo: String = member.photo,
                      private var mFullName: String = member.getFullName()) : BaseViewModel() {


    override fun getTypeIdLayout() = LayoutTypes.Member


    override fun onCreateViewHolder(view: View): BaseViewHolder<BaseViewModel> {
        return MemberViewHolder(view) as BaseViewHolder<BaseViewModel>
    }

   private class MemberViewHolder(view: View) : BaseViewHolder<MemberViewModel>(view) {

        private val civProfilePhoto = view.civ_profile_image
        private val profileName = view.tv_profile_name


        override fun bindViewHolder(itemModel: MemberViewModel) {
            val context = itemView.context

            Glide.with(context)
                    .load(itemModel.photo)
                    .into(civProfilePhoto)

            profileName.text = itemModel.mFullName

        }

        override fun unbindViewHolder() {
            civProfilePhoto.setImageBitmap(null)
            profileName.text = null
        }

    }
}