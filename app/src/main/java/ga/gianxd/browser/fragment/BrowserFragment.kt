package ga.gianxd.browser.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ga.gianxd.browser.MainActivity
import ga.gianxd.browser.databinding.FragmentBrowserBinding

class BrowserFragment : Fragment() {
    private var _binding: FragmentBrowserBinding? = null
    private val binding get() = _binding!!

    private lateinit var actionBar: ActionBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBrowserBinding.inflate(inflater, container, false)
        actionBar = (requireActivity() as AppCompatActivity).supportActionBar!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.webView.canGoBack()) {
                        binding.webView.goBack()
                        return
                    }

                    this.isEnabled = false
                    (requireActivity() as AppCompatActivity).onBackPressed()
                }
            })

        binding.settingsButton.setOnClickListener {
            (requireActivity() as MainActivity).switch(MainActivity.FRAGMENT_PREFERENCES)
        }

        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl("https://github.com/gianxddddd/archo-browser")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        actionBar.hide()
    }

    override fun onStop() {
        super.onStop()
        actionBar.show()
    }
}