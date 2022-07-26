package ga.gianxd.browser

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE
import androidx.fragment.app.commit
import ga.gianxd.browser.databinding.ActivityMainBinding
import ga.gianxd.browser.fragment.BrowserFragment
import ga.gianxd.browser.fragment.PreferencesFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var browserFragment: BrowserFragment
    private lateinit var preferencesFragment: PreferencesFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createFragments(savedInstanceState)
        createRootView()
        initialize()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * Switches current fragment with a different fragment by using the FRAGMENT_* variables
     *
     * @param i The fragment that is going to replace the current one
     */
    fun switch(i: Int) {
        var fragment = Fragment()

        if (i == FRAGMENT_BROWSER) fragment = browserFragment
        if (i == FRAGMENT_PREFERENCES) fragment = preferencesFragment

        supportFragmentManager.commit {
            replace(R.id.mainFragmentContainer, fragment)
            setTransition(TRANSIT_FRAGMENT_FADE)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    private fun initialize() {
        supportFragmentManager.commit {
            add(R.id.mainFragmentContainer, browserFragment)
            setReorderingAllowed(true)
        }
    }

    private fun createFragments(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) return
        browserFragment = BrowserFragment()
        preferencesFragment = PreferencesFragment()
    }

    private fun createRootView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    companion object {
        const val FRAGMENT_BROWSER: Int = 0
        const val FRAGMENT_PREFERENCES: Int = 1
    }
}