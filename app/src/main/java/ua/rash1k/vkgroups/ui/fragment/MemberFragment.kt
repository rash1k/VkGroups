package ua.rash1k.vkgroups.ui.fragment

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.mvp.presenter.BaseFeedPresenter
import ua.rash1k.vkgroups.mvp.presenter.MembersPresenter
import ua.rash1k.vkgroups.mvp.view.BaseFeedView


class MemberFragment : BaseFeedFragment() {
    @InjectPresenter
    lateinit var mPresenter: MembersPresenter


    override fun onCreateFeedPresenter(): BaseFeedPresenter<BaseFeedView> {
        return mPresenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        MyApplication.sApplicationComponent.inject(this)
    }

    @Override
    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_members
    }

}