package ga.gianxd.browser.view

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible

class BrowserFaviconView : AppCompatImageView {
    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet?): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    init { hideIfShown() }

    fun setFavicon(bm: Bitmap?) {
        if (bm == null) hideIfShown()
        else showIfHidden()

        setImageBitmap(bm)
    }

    private fun showIfHidden() {
        if (!isVisible) isVisible = true
    }

    private fun hideIfShown() {
        if (isVisible) isVisible = false
    }
}