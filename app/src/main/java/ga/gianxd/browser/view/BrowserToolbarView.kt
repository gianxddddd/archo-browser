package ga.gianxd.browser.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView

class BrowserToolbarView(context: Context, attrs: AttributeSet, defStyleAttrs: Int)
    : LinearLayout(context, attrs, defStyleAttrs) {
    init {
        orientation = HORIZONTAL
        val text = TextView(context, attrs, defStyleAttrs)
        text.text = "Fuck"
        addView(text)
    }
}