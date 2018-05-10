package ua.rash1k.vkgroups.ui.fragment

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.mvp.presenter.BaseFeedPresenter
import ua.rash1k.vkgroups.mvp.presenter.NewsPresenter
import ua.rash1k.vkgroups.mvp.view.BaseFeedView


class NewsFragment : BaseFeedFragment() {

    @InjectPresenter
    lateinit var mPresenter: NewsPresenter

    override fun onCreateFeedPresenter(): BaseFeedPresenter<BaseFeedView> {
        return mPresenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        MyApplication.sApplicationComponent.inject(this)
    }


    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_news_feed_title
    }
}