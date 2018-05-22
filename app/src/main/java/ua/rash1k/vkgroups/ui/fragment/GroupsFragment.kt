package ua.rash1k.vkgroups.ui.fragment

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.mvp.presenter.BaseFeedPresenter
import ua.rash1k.vkgroups.mvp.presenter.GroupsPresenter
import ua.rash1k.vkgroups.mvp.view.BaseFeedView

class GroupsFragment : BaseFeedFragment() {


    @InjectPresenter
    lateinit var mPresenter: GroupsPresenter

    override fun onCreateFeedPresenter(): BaseFeedPresenter<BaseFeedView> {
        return mPresenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.appId = context?.resources?.getInteger(R.integer.com_vk_sdk_AppId)!!
    }

    override fun onCreateToolbarTitle(): Int {
        return R.string.title_fragment_groups
    }

}
