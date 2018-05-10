package ua.rash1k.vkgroups.ui.view.holder.attachment

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_attachment_image.view.*
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.common.manager.MyFragmentManager
import ua.rash1k.vkgroups.models.view.attachment.ImageAttachmentViewModel
import ua.rash1k.vkgroups.ui.activity.BaseActivity
import ua.rash1k.vkgroups.ui.fragment.ImageFragment
import ua.rash1k.vkgroups.ui.view.holder.BaseViewHolder
import javax.inject.Inject


class ImageAttachmentHolder(view: View) : BaseViewHolder<ImageAttachmentViewModel>(view) {

    var image: ImageView = view.iv_attachment_image

    @Inject
    lateinit var myFragmentManager: MyFragmentManager


    init {
        MyApplication.sApplicationComponent.inject(this)
    }

    override fun bindViewHolder(itemModel: ImageAttachmentViewModel) {

        if (itemModel.needClick) {
            itemView.setOnClickListener {
                myFragmentManager.addFragment(it.context as BaseActivity,
                        ImageFragment.newInstance(itemModel.mPhotoUrl), R.id.main_wrapper)
            }
        }


        Glide.with(itemView.context)
                .load(itemModel.mPhotoUrl)
                .into(image)
    }

    override fun unbindViewHolder() {
        image.setOnClickListener(null)
        image.setImageBitmap(null)
    }
}