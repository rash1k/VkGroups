package ua.rash1k.vkgroups.ui.fragment


import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_base.*
import ua.rash1k.vkgroups.MyApplication
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.models.Place
import ua.rash1k.vkgroups.mvp.presenter.BaseFeedPresenter
import ua.rash1k.vkgroups.mvp.presenter.CommentsPresenter
import ua.rash1k.vkgroups.mvp.view.BaseFeedView
import ua.rash1k.vkgroups.ui.activity.CreatePostActivity

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


    override fun onResume() {
        super.onResume()
        getBaseActivity().fab.setOnClickListener {
            val postActivityIntent = Intent(getBaseActivity(), CreatePostActivity::class.java)
            postActivityIntent.putExtra("type", "comment")
            postActivityIntent.putExtra("owner_id", mPlace.ownerId)
            postActivityIntent.putExtra("id", mPlace.postId)

            getBaseActivity().startActivityForResult(postActivityIntent, 0)
        }
    }

    override fun needFab(): Boolean {
        return true
    }


    override fun onCreateFeedPresenter(): BaseFeedPresenter<BaseFeedView> {
        mPresenter.mPlace = mPlace
        return mPresenter
    }


    public override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_comments
    }
}