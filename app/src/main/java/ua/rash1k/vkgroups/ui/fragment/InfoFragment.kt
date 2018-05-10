package ua.rash1k.vkgroups.ui.fragment

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.mvp.presenter.BaseFeedPresenter
import ua.rash1k.vkgroups.mvp.presenter.InfoPresenter
import ua.rash1k.vkgroups.mvp.view.BaseFeedView


class InfoFragment : BaseFeedFragment() {


    @InjectPresenter
    lateinit var mInfoPresenter: InfoPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isWithEndlessList = false
    }

    override fun onCreateFeedPresenter(): BaseFeedPresenter<BaseFeedView> {
        return mInfoPresenter
    }

    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_info
    }
}