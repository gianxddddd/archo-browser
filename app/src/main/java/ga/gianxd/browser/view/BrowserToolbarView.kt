package ga.gianxd.browser.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import ga.gianxd.browser.R
import ga.gianxd.browser.databinding.ViewBrowserToolbarBinding

class BrowserToolbarView : FrameLayout {
    var mode = MODE_DISPLAY

    lateinit var security: ImageButton
    lateinit var favicon: BrowserFaviconView
    lateinit var url: EditText
    lateinit var clear: ImageButton
    lateinit var tabs: ImageButton
    lateinit var menu: ImageButton
    lateinit var progress: ProgressBar

    private val tag = "BrowserToolbarView"

    private lateinit var binding: ViewBrowserToolbarBinding
    private lateinit var toolBarLayout: LinearLayout
    private lateinit var editLayout: LinearLayout
    private lateinit var urlReadable: TextView

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet?): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    init {
        inflateView()
        findChildViews()
        registerListeners()
        progress.isVisible = false
        toggle(MODE_DISPLAY)
    }

    fun toggle(mode: Int) {
        if (mode == MODE_DISPLAY) {
            if (url.hasFocus()) url.clearFocus()

            security.isVisible = true
            urlReadable.isVisible = true
            url.isVisible = false
            clear.isVisible = false
            tabs.isVisible = true
            menu.isVisible = true
        } else if (mode == MODE_EDIT) {
            url.setSelection(url.length())
            security.isVisible = false
            urlReadable.isVisible = false
            url.isVisible = true
            clear.isVisible = true
            tabs.isVisible = false
            menu.isVisible = false

            if (!url.hasFocus()) url.requestFocus()
        } else {
            Log.w(tag, "toggle() is called, but mode is invalid.")
            return
        }

        this.mode = mode
    }

    private fun findChildViews() {
        security = binding.security
        favicon = binding.favicon
        urlReadable = binding.urlReadable
        url = binding.url
        clear = binding.clear
        tabs = binding.tabs
        menu = binding.menu
        progress = binding.progress

        toolBarLayout = binding.toolbarLayout
        editLayout = binding.editLayout
    }

    private fun registerListeners() {
        url.addTextChangedListener { text ->
            urlReadable.text = text
        }
        url.setOnFocusChangeListener { view, hasFocus ->
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (hasFocus) imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            else if (!hasFocus) toggle(MODE_DISPLAY).also { imm.hideSoftInputFromWindow(windowToken, 0) }
        }

        urlReadable.setOnClickListener { toggle(MODE_EDIT) }
        clear.setOnClickListener { url.text.clear() }
    }

    private fun inflateView() {
        binding = ViewBrowserToolbarBinding.bind(View.inflate(context, R.layout.view_browser_toolbar, this))
    }

    companion object {
        const val MODE_DISPLAY = 0
        const val MODE_EDIT = 1
    }
}