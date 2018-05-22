package ua.rash1k.vkgroups.ui.fragment

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_opened_wall_item.*
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.models.view.NewsItemFooterViewModel
import ua.rash1k.vkgroups.mvp.presenter.BaseFeedPresenter
import ua.rash1k.vkgroups.mvp.presenter.OpenedPostPresenter
import ua.rash1k.vkgroups.mvp.view.BaseFeedView
import ua.rash1k.vkgroups.mvp.view.OpenedPostView
import ua.rash1k.vkgroups.ui.view.holder.NewsItemFooterHolder


class OpenedPostFragment : BaseFeedFragment(), OpenedPostView {


    @InjectPresenter
    lateinit var mPresenter: OpenedPostPresenter

    private var idPost: Int = 0


    companion object {
        fun newInstance(id: Int): OpenedPostFragment {
            val args = Bundle()
            args.putInt("id", id)
            val openedPostFragment = OpenedPostFragment()
            openedPostFragment.arguments = args
            return openedPostFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.sApplicationComponent.inject(this)

        isWithEndlessList = false

        if (arguments != null) {
            idPost = arguments!!.getInt("id")
        }
    }

    override fun setFooter(viewModel: NewsItemFooterViewModel) {
        rv_footer.visibility = View.VISIBLE
        NewsItemFooterHolder(rv_footer).bindViewHolder(viewModel)
    }


    override fun onCreateFeedPresenter(): BaseFeedPresenter<BaseFeedView> {
        mPresenter.id = idPost
        return mPresenter as BaseFeedPresenter<BaseFeedView>
    }

    override fun getMainContentLayout(): Int {
        return R.layout.fragment_opened_wall_item
    }

    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_opened_post
    }
}