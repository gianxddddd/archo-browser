package ga.gianxd.browser.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import ga.gianxd.browser.MainActivity
import ga.gianxd.browser.databinding.FragmentBrowserBinding
import ga.gianxd.browser.view.BrowserToolbarView
import org.mozilla.geckoview.GeckoRuntime
import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoSessionSettings

class BrowserFragment : Fragment() {
    private var _binding: FragmentBrowserBinding? = null
    private val binding get() = _binding!!

    private lateinit var actionBar: ActionBar
    private lateinit var webSession: GeckoSession

    private var canGoBack = false
    private var canGoForward = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webUserAgent = "Mozilla/5.0 (Linux; Android ${android.os.Build.VERSION.RELEASE}; ${android.os.Build.MODEL}) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) ArchoBrowser/100.0.20220425210429 Mobile Safari/537.36"
        val webSessionSettings = GeckoSessionSettings.Builder()
            .allowJavascript(true)
            .userAgentOverride(webUserAgent)
            .build()

        webRuntime = GeckoRuntime.create(requireContext())
        webSession = GeckoSession(webSessionSettings)

        webSession.contentDelegate = object: GeckoSession.ContentDelegate {}
        webSession.progressDelegate = object: GeckoSession.ProgressDelegate {
            override fun onPageStart(session: GeckoSession, url: String) {
                super.onPageStart(session, url)
                binding.toolbar.progress.isVisible = true

                if (binding.toolbar.url.text.toString() != url)
                    binding.toolbar.url.setText(url)
            }

            override fun onPageStop(session: GeckoSession, success: Boolean) {
                super.onPageStop(session, success)
                binding.toolbar.progress.isVisible = false
            }

            override fun onProgressChange(session: GeckoSession, progress: Int) {
                super.onProgressChange(session, progress)
                binding.toolbar.progress.progress = progress
            }
        }
        webSession.navigationDelegate = object: GeckoSession.NavigationDelegate {
            override fun onLocationChange(session: GeckoSession, url: String?) {
                super.onLocationChange(session, url)
                binding.toolbar.url.setText(url)
            }

            override fun onCanGoBack(session: GeckoSession, canGoBack: Boolean) {
                super.onCanGoBack(session, canGoBack)
                this@BrowserFragment.canGoBack = canGoBack
            }

            override fun onCanGoForward(session: GeckoSession, canGoForward: Boolean) {
                super.onCanGoForward(session, canGoForward)
                this@BrowserFragment.canGoForward = canGoForward
            }
        }
    }

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
                    if (canGoBack) {
                        webSession.goBack()
                        return
                    }

                    this.isEnabled = false
                    (requireActivity() as AppCompatActivity).onBackPressed()
                }
            })

        binding.toolbar.url.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                binding.toolbar.toggle(BrowserToolbarView.MODE_DISPLAY)
                webSession.loadUri(textView.text.toString())
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }
        binding.toolbar.tabs.setOnClickListener {
            Toast.makeText(context, "TODO: Implement browser tabs", Toast.LENGTH_LONG).show()
        }
        binding.toolbar.menu.setOnClickListener {
            Toast.makeText(context, "TODO: Implement menu", Toast.LENGTH_LONG).show()
        }
        binding.settingsButton.setOnClickListener {
            (requireActivity() as MainActivity).switch(MainActivity.FRAGMENT_PREFERENCES)
        }

        if (webRuntime != null && !webSession.isOpen) webSession.open(webRuntime!!)
        binding.webContents.setSession(webSession)
        webSession.loadUri("https://github.com/gianxddddd/archo-browser")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.webContents.releaseSession()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        webRuntime?.shutdown()
    }

    override fun onStart() {
        super.onStart()
        actionBar.hide()
    }

    override fun onStop() {
        super.onStop()
        actionBar.show()
    }

    companion object {
        private var webRuntime: GeckoRuntime? = null
    }
}
