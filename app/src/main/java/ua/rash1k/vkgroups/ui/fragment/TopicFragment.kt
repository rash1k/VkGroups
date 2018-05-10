package ua.rash1k.vkgroups.ui.fragment

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.mvp.presenter.BaseFeedPresenter
import ua.rash1k.vkgroups.mvp.presenter.BoardPresenter
import ua.rash1k.vkgroups.mvp.view.BaseFeedView

class TopicFragment : BaseFeedFragment() {

    @InjectPresenter
    lateinit var mPresenter: BoardPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        MyApplication.sApplicationComponent.inject(this)
    }

    override fun onCreateFeedPresenter(): BaseFeedPresenter<BaseFeedView> {
        return mPresenter
    }

    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_topics
    }
}
