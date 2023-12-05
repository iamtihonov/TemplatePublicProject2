package ua.artem.template.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.util.TypedValue
import ua.good.utils.base.BaseUtil
import java.util.Random

/**
 * Класс помощник работы с View
 */
abstract class IViewUtil : BaseUtil() {
    abstract fun dpToPx(valueInDp: Float): Float
    abstract fun pixelsToSp(px: Float): Float
    abstract fun pixelsToDp(px: Float): Float
    abstract fun drawableToBitmap(drawable: Drawable): Bitmap
    abstract fun isColorDark(color: Int): Boolean
    abstract fun rotateBitmap(source: Bitmap, angle: Float): Bitmap
    abstract fun generateColor(): Int
}

class ViewUtil(private val context: Context) : IViewUtil() {

    override fun dpToPx(valueInDp: Float): Float {
        val metrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics)
    }

    override fun pixelsToSp(px: Float): Float {
        val scaledDensity = context.resources.displayMetrics.scaledDensity
        return px / scaledDensity
    }

    override fun pixelsToDp(px: Float): Float {
        val densityDpi = context.resources.displayMetrics.densityDpi.toFloat()
        return px / (densityDpi / DisplayMetrics.DENSITY_DEFAULT)
    }

    override fun drawableToBitmap(drawable: Drawable): Bitmap {
        val intrinsicWidth = drawable.intrinsicWidth
        val intrinsicHeight = drawable.intrinsicHeight
        val bitmap: Bitmap = if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
            // Single color bitmap will be created of 1x1 pixel
            Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        } else {
            Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
        }

        if (drawable is BitmapDrawable && drawable.bitmap != null) {
            return drawable.bitmap
        }

        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    @Suppress("MagicNumber")
    override fun isColorDark(color: Int): Boolean {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)

        val darkness = 1 - (0.299 * red + 0.587 * green + 0.114 * blue) / 255
        return darkness >= 0.5
    }

    override fun rotateBitmap(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }

    @Suppress("MagicNumber")
    override fun generateColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }
}
