package ua.rash1k.vkgroups.ui.fragment

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.mvp.presenter.BaseFeedPresenter
import ua.rash1k.vkgroups.mvp.presenter.OpenedCommentPresenter
import ua.rash1k.vkgroups.mvp.view.BaseFeedView


class OpenedCommentFragment : BaseFeedFragment() {


    private var _id: Int = 0

    @InjectPresenter
    lateinit var openedCommentPresenter: OpenedCommentPresenter


    companion object {
        fun newInstance(id: Int): OpenedCommentFragment {
            val args = Bundle()
            args.putInt("id", id)
            val openedCommentFragment = OpenedCommentFragment()
            openedCommentFragment.arguments = args
            return openedCommentFragment
        }
    }

    override fun getMainContentLayout(): Int {
        return R.layout.fragment_opened_wall_item
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        MyApplication.sApplicationComponent.inject(this)
        super.onCreate(savedInstanceState)

        _id = arguments?.getInt("id", 0) ?: 0

        isWithEndlessList = false
    }

    override fun onCreateFeedPresenter(): BaseFeedPresenter<BaseFeedView> {
        openedCommentPresenter.id = _id
        return openedCommentPresenter
    }

    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_opened_comment
    }
}