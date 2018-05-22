package ua.rash1k.vkgroups.ui.fragment

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.models.Place
import ua.rash1k.vkgroups.mvp.presenter.BaseFeedPresenter
import ua.rash1k.vkgroups.mvp.presenter.TopicCommentsPresenter
import ua.rash1k.vkgroups.mvp.view.BaseFeedView


class TopicCommentsFragment : BaseFeedFragment() {

    @InjectPresenter
    lateinit var mPresenter: TopicCommentsPresenter

    private lateinit var mPlace: Place


    companion object {
        fun newInstance(place: Place): TopicCommentsFragment {
            val args = Bundle()
            args.putAll(place.toBundle())

            val fragment = TopicCommentsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isWithEndlessList = true
        MyApplication.sApplicationComponent.inject(this)
        mPlace = Place(arguments!!)

    }


    override fun onCreateFeedPresenter(): BaseFeedPresenter<BaseFeedView> {
        mPresenter.mPlace = mPlace
        return mPresenter
    }


    public override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_topic_comments
    }
}