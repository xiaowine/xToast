package app.xiaowine.xtoast

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

@Suppress("DEPRECATION")
object XToast {
    private var lastToast: Toast? = null
    const val LENGTH_SHORT = 0
    private const val LENGTH_LONG = 1

    @SuppressLint("ShowToast")
//    fun makeText(context: Context, message: CharSequence, duration: Int = LENGTH_LONG, currentTypeface: Typeface = Typeface.create("sans-serif-condensed", Typeface.NORMAL), textSize: Int = 16, textColor: Int = R.color.textColor, toastIcon: Drawable? = null, elevations: Float = 15f, allowQueue: Boolean = true, toastGravity: Int = -1, xOffsets: Int = -1, yOffsets: Int = -1, isRTL: Boolean = false): Toast {
    fun makeText(context: Context, message: CharSequence, duration: Int = LENGTH_LONG, currentTypeface: Typeface = Typeface.create("sans-serif-condensed", Typeface.NORMAL), textSize: Int = 16, textColor: Int = R.color.textColor, toastIcon: Drawable? = null, elevations: Float = 15f, allowQueue: Boolean = true, isRTL: Boolean = false): Toast {
        val toastLayout = View.inflate(context, R.layout.toast_layout, null).apply {
            val toastDrawable = context.resources.getDrawable(R.drawable.toast_frame)
            toastDrawable.setTint(context.resources.getColor(R.color.backgroundColor))
            background = toastDrawable
            elevation = elevations
        }
        toastLayout.findViewById<LinearLayout>(R.id.toast_root).apply {
            if (isRTL) {
                layoutDirection = View.LAYOUT_DIRECTION_RTL
            }
        }
        toastLayout.findViewById<ImageView>(R.id.toast_icon).apply {
            background = toastIcon
            if (toastIcon == null) {
                visibility = View.GONE
            }
        }

        toastLayout.findViewById<TextView>(R.id.toast_text).apply {
            text = message
            setTextColor(context.resources.getColor(textColor))
            typeface = currentTypeface
            setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize.toFloat())
        }

        val currentToast = Toast.makeText(context, "", duration).apply {
            view = toastLayout
//            setGravity(if (toastGravity == -1) gravity else toastGravity, if (xOffset == -1) xOffset else xOffsets, if (yOffset == -1) yOffset else yOffsets)
        }

        if (!allowQueue) {
            if (lastToast != null) {
                lastToast!!.cancel()
            }
            lastToast = currentToast
        }
        return currentToast
    }
}