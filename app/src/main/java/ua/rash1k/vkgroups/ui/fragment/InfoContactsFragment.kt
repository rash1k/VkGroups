package ua.rash1k.vkgroups.ui.fragment

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.mvp.presenter.BaseFeedPresenter
import ua.rash1k.vkgroups.mvp.presenter.InfoContactsPresenter
import ua.rash1k.vkgroups.mvp.view.BaseFeedView


class InfoContactsFragment : BaseFeedFragment() {


    @InjectPresenter
    lateinit var mPresenter: InfoContactsPresenter

    override fun onCreateFeedPresenter(): BaseFeedPresenter<BaseFeedView> {
        return mPresenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isWithEndlessList = false
    }

    override fun onCreateToolbarTitle(): Int {
        return R.string.title_contacts
    }
}