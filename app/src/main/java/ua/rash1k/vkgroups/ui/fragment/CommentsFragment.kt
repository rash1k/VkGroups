package ua.rash1k.vkgroups.ui.fragment


import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.models.Place
import ua.rash1k.vkgroups.mvp.presenter.BaseFeedPresenter
import ua.rash1k.vkgroups.mvp.presenter.CommentsPresenter
import ua.rash1k.vkgroups.mvp.view.BaseFeedView

class CommentsFragment : BaseFeedFragment() {

    @InjectPresenter
    lateinit var mPresenter: CommentsPresenter

    lateinit var mPlace: Place


    companion object {
        fun newInstance(place: Place): CommentsFragment {
            val args = Bundle()
            args.putAll(place.toBundle())

            val fragment = CommentsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.sApplicationComponent.inject(this)
        mPlace = Place(arguments!!)
    }


/*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
*/

    override fun onCreateFeedPresenter(): BaseFeedPresenter<BaseFeedView> {
        mPresenter.setPlace(mPlace)
        return mPresenter
    }


    public override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_comments
    }
}