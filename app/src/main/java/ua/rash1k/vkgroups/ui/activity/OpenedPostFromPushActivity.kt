package ua.rash1k.vkgroups.ui.activity

import android.os.Bundle
import android.util.Log
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.common.utils.startActivity
import ua.rash1k.vkgroups.models.Place
import ua.rash1k.vkgroups.ui.fragment.OpenedPostFragment


class OpenedPostFromPushActivity : BaseActivity() {

    private lateinit var mPlace: Place

    override val TAG = BaseActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val bundle = intent.getBundleExtra(Place.PLACE)

        mPlace = Place(bundle)


        myFragmentManager.addFragment(this,
                OpenedPostFragment.newInstance(mPlace.postId.toInt()), R.id.main_wrapper)

    }

    override fun onBackPressed() {

        Log.d(TAG, "BackStack:  ${supportFragmentManager.backStackEntryCount}")

        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
            super.onBackPressed()
        } else {
            startActivity<MainActivity>()
            finish()
        }
    }

    override fun getMainContentLayout(): Int {
        return R.layout.activity_opened_post_from_push
    }
}