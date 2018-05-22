package ua.rash1k.vkgroups.ui.activity

import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import ua.rash1k.vkgroups.R
import ua.rash1k.vkgroups.ui.fragment.MyPreferencesFragment


class SettingActivity : BaseActivity() {

    override fun getMainContentLayout() = R.layout.activity_setting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager.beginTransaction().replace(R.id.main_wrapper,
                MyPreferencesFragment()).commit()

        setupActionBar()
    }

    private fun setupActionBar() {
        toolbar.setTitle(R.string.title_activity_settings)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        fab.hide()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        when (id) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}