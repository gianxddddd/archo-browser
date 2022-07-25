package ga.gianxd.browser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import ga.gianxd.browser.databinding.ActivityMainBinding
import ga.gianxd.browser.fragment.BrowserFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createRootView()

        if (savedInstanceState == null) createDefaultFragment()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * Switches current fragment to a different fragment
     */
    inline fun <reified F : Fragment> switch() {
        supportFragmentManager.commit {
            replace<F>(R.id.mainFragmentContainer)
            setTransition(TRANSIT_FRAGMENT_FADE)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    private fun createDefaultFragment() {
        supportFragmentManager.commit {
            add<BrowserFragment>(R.id.mainFragmentContainer)
            setReorderingAllowed(true)
        }
    }

    private fun createRootView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}