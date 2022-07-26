package ga.gianxd.browser.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import ga.gianxd.browser.R
import ga.gianxd.browser.databinding.FragmentBrowserChildBinding

class BrowserChildFragment : Fragment() {
    private var _binding: FragmentBrowserChildBinding? = null
    private val binding get() = _binding!!

    private lateinit var preferences: SharedPreferences

    @Suppress
    lateinit var webRenderer: View

    @Suppress
    var webRendererId = "mozilla"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        webRendererId = if (preferences.contains("renderer"))
            preferences.getString("renderer", "mozilla")!! else webRendererId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBrowserChildBinding.inflate(inflater, container, false)

        if (webRendererId == "mozilla") initializeGecko()
        else if (webRendererId == "chromium") initializeBlink()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (webRendererId == "chromium") {
            (webRenderer as WebView).loadUrl("https://google.com")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeBlink() {
        webRenderer = WebView(requireContext())
        webRenderer.layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT, 1f)
        (webRenderer as WebView).webViewClient = WebViewClient()
        binding.root.addView(webRenderer)
    }

    private fun initializeGecko() {
        Toast.makeText(requireContext(), R.string.toast_warn_mozilla_renderer, Toast.LENGTH_LONG).show()
    }
}