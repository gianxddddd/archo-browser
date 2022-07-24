package ga.gianxd.browser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ga.gianxd.browser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createRootView()
        loadTempUrl()
    }

    private fun loadTempUrl() {
        binding.testWebView.loadUrl("https://gianxd.ga")
    }

    private fun createRootView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}