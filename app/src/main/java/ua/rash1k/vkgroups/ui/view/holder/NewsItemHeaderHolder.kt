package ua.rash1k.vkgroups.ui.view.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_news_header.view.*
import ua.rash1k.vkgroups.models.view.NewsItemHeaderViewModel

class NewsItemHeaderHolder(itemView: View,
                           private val civProfileImage: CircleImageView = itemView.civ_profile_image,
                           private val tvName: TextView = itemView.tv_profile_name,
                           private val ivRepostedIcon: ImageView = itemView.iv_reposted_icon,
                           private val tvRepostedProfileName: TextView = itemView.tv_reposted_profile_name)
    : BaseViewHolder<NewsItemHeaderViewModel>(itemView) {


    override fun bindViewHolder(item: NewsItemHeaderViewModel) {
        val context = itemView.context

        //Загружаем изображение
        Glide.with(context)
                .load(item.mProfilePhoto)
                .into(civProfileImage)

        tvName.text = item.mProfileName

        val mIsReposts = item.mIsReposts

        if (mIsReposts != null && mIsReposts) {
            ivRepostedIcon.visibility = View.VISIBLE
            tvRepostedProfileName.text = item.mRepostsProfileName
        } else {
            ivRepostedIcon.visibility = View.GONE
            tvRepostedProfileName.text = null
        }

    }

    override fun unbindViewHolder() {
        civProfileImage.setImageBitmap(null)
        tvName.text = null
        tvRepostedProfileName.text = null
    }

}
