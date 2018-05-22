package ua.rash1k.vkgroups.ui.fragment

import android.os.Bundle
import android.preference.PreferenceFragment
import ua.rash1k.vkgroups.R


class MyPreferencesFragment : PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)
    }
}