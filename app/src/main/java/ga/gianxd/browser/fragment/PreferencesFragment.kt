package ga.gianxd.browser.fragment

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.preference.CheckBoxPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import ga.gianxd.browser.MainActivity
import ga.gianxd.browser.R

class PreferencesFragment : PreferenceFragmentCompat() {
    private lateinit var actionBar: ActionBar
    private lateinit var renderer: ListPreference
    private lateinit var swRender: CheckBoxPreference
    private lateinit var deleteData: Preference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar = (requireActivity() as MainActivity).supportActionBar!!
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        renderer = findPreference("renderer")!!
        swRender = findPreference("swRender")!!
        deleteData = findPreference("deleteData")!!
        registerListeners()
    }

    override fun onStart() {
        super.onStart()
        actionBar.title = "Settings"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStop() {
        super.onStop()
        actionBar.title = ""
        actionBar.setDisplayHomeAsUpEnabled(false)
    }

    private fun registerListeners() {
        renderer.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, value ->
            if ((preference as ListPreference).value == value) {
                return@OnPreferenceChangeListener false
            }

            Toast.makeText(requireContext(),
                R.string.toast_restart_renderer,
                Toast.LENGTH_LONG).show()

            true
        }
        swRender.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, value ->
            if ((value as Boolean)) {
                Toast.makeText(requireContext(),
                    R.string.toast_sw_render_warn,
                    Toast.LENGTH_LONG).show()
            }

            true
        }
        deleteData.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.dialog_delete_data_title)
                .setMessage(R.string.dialog_delete_data_message)
                .setPositiveButton(R.string.dialog_button_yes) { _, _ ->
                    Toast.makeText(requireContext(), R.string.toast_delete_browsing_data, Toast.LENGTH_LONG).show()
                }.setNegativeButton(R.string.dialog_button_no) { _, _ -> }
                .show()

            true
        }
    }
}