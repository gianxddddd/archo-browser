package ga.gianxd.browser.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import ga.gianxd.browser.R

class BrowserToolbarView : LinearLayout {
    var mode = MODE_DISPLAY

    lateinit var security: ImageButton
    lateinit var favicon: ImageView
    lateinit var url: EditText
    lateinit var cancel: ImageButton
    lateinit var menu: ImageButton
    lateinit var progress: ProgressBar

    private val tag = "BrowserToolbarView"

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
        hideProgress()
        toggle(MODE_DISPLAY)
    }

    fun toggle(mode: Int) {
        if (mode == MODE_DISPLAY) {
            if (url.hasFocus()) url.clearFocus()

            security.isVisible = true
            urlReadable.isVisible = true
            url.isVisible = false
            cancel.isVisible = false
            menu.isVisible = true
        } else if (mode == MODE_EDIT) {
            security.isVisible = false
            urlReadable.isVisible = false
            url.isVisible = true
            cancel.isVisible = true
            menu.isVisible = false

            if (!url.hasFocus()) url.requestFocus()
        } else {
            Log.w(tag, "toggle() is called, but mode is invalid.")
            return
        }

        this.mode = mode
    }

    private fun findChildViews() {
        security = findViewById(R.id.security)
        favicon = findViewById(R.id.favicon)
        urlReadable = findViewById(R.id.urlReadable)
        url = findViewById(R.id.url)
        cancel = findViewById(R.id.cancel)
        menu = findViewById(R.id.menu)
        progress = findViewById(R.id.progress)

        toolBarLayout = findViewById(R.id.toolbarLayout)
        editLayout = findViewById(R.id.editLayout)
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
        cancel.setOnClickListener { toggle(MODE_DISPLAY) }
    }

    private fun hideProgress() {
        progress.isVisible = false
    }

    private fun inflateView() {
        LayoutInflater.from(context).inflate(R.layout.view_browser_toolbar, this, true)
    }

    companion object {
        const val MODE_DISPLAY = 0
        const val MODE_EDIT = 1
    }
}