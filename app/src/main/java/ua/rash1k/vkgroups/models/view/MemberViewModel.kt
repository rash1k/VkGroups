package ua.rash1k.vkgroups.models.view

import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_member.view.*
import ua.rash1k.vkgroups.models.Member
import ua.rash1k.vkgroups.models.Profile
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder


class MemberViewModel : BaseViewModel {


    private var id: Int = 0
    private var groupId: Int = 0
    private var photo: String
    private var mFullName: String

    constructor(member: Member) {
        id = member.id
        groupId = member.group_id
        photo = member.photo
        mFullName = member.getFullName()
    }

    constructor(profile: Profile) {
        photo = profile.getDisplayProfilePhoto()!!
        mFullName = profile.getFullName()
    }

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