package ga.gianxd.browser.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import ga.gianxd.browser.MainActivity
import ga.gianxd.browser.databinding.FragmentBrowserBinding
import ga.gianxd.browser.view.BrowserToolbarView

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
                    if (binding.toolbar.mode == BrowserToolbarView.MODE_EDIT) {
                        binding.toolbar.toggle(BrowserToolbarView.MODE_DISPLAY)
                        return
                    }
                    if (binding.webView.canGoBack()) {
                        binding.webView.goBack()
                        return
                    }

                    this.isEnabled = false
                    (requireActivity() as AppCompatActivity).onBackPressed()
                }
            })

        binding.toolbar.url.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                binding.toolbar.toggle(BrowserToolbarView.MODE_DISPLAY)
                binding.webView.loadUrl(textView.text.toString())
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }
        binding.toolbar.tabs.setOnClickListener {
            Toast.makeText(context, "TODO: Implement browser tabs", Toast.LENGTH_LONG).show()
        }
        binding.settingsButton.setOnClickListener {
            (requireActivity() as MainActivity).switch(MainActivity.FRAGMENT_PREFERENCES)
        }

        binding.webView.webViewClient = object: WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.toolbar.progress.isVisible = true

            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.toolbar.progress.isVisible = false
                binding.toolbar.url.setText(url)
            }
        }
        binding.webView.webChromeClient = object: WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                binding.toolbar.progress.progress = newProgress
            }

            override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
                super.onReceivedIcon(view, icon)
                binding.toolbar.favicon.setImageBitmap(icon)
            }
        }

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
