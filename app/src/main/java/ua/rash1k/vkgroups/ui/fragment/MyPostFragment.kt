package ua.rash1k.vkgroups.ui.fragment

import android.os.Bundle
import ua.rash1k.vkgroups.R


class MyPostFragment : NewsFeedFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.enableIdFiltering = true
    }


    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_my_posts
    }
}