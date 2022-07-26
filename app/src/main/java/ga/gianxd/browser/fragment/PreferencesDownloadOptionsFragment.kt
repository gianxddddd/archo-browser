package ga.gianxd.browser.fragment

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.preference.PreferenceFragmentCompat
import ga.gianxd.browser.MainActivity
import ga.gianxd.browser.R

class PreferencesDownloadOptionsFragment : PreferenceFragmentCompat() {
    private lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar = (requireActivity() as MainActivity).supportActionBar!!
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_download_options, rootKey)
    }

    override fun onStart() {
        super.onStart()
        actionBar.title = "Download Options"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStop() {
        super.onStop()
        actionBar.title = ""
        actionBar.setDisplayHomeAsUpEnabled(false)
    }
}