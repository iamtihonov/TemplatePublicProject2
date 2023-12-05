package ua.artem.template.application.extensions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.os.SystemClock
import android.view.TouchDelegate
import android.view.View
import androidx.annotation.DimenRes

/**
 * Между выполнениями, должно пройти определенное время
 * button.onClick(debounceDuration = 500L) { ... }
 */
@Suppress("unused")
fun View.onClick(debounceDuration: Long = 300L, action: (View) -> Unit) {
    setOnClickListener(DebouncedOnClickListener(debounceDuration) { action(it) })
}

private class DebouncedOnClickListener(
    private val debounceDuration: Long,
    private val clickAction: (View) -> Unit
) : View.OnClickListener {

    private var lastClickTime: Long = 0

    override fun onClick(v: View) {
        val now = SystemClock.elapsedRealtime()
        if (now - lastClickTime >= debounceDuration) {
            lastClickTime = now
            clickAction(v)
        }
    }
}

/**
 * Добавляет область для нажатия view
 */
fun View.addTouchDelegate(
    @DimenRes leftId: Int,
    @DimenRes topId: Int,
    @DimenRes rightId: Int,
    @DimenRes bottomId: Int
) {
    val res = this.resources
    val left = res.getDimensionPixelSize(leftId)
    val top = res.getDimensionPixelSize(topId)
    val right = res.getDimensionPixelSize(rightId)
    val bottom = res.getDimensionPixelSize(bottomId)

    val parent = this.parent as? View
    parent?.post {
        val r = Rect()
        this.getHitRect(r)
        r.left -= left
        r.top -= top
        r.right += right
        r.bottom += bottom
        parent.touchDelegate = TouchDelegate(r, this)
    }
}

@Suppress("unused")
fun View.addTouchDelegate(@DimenRes id: Int) {
    this.addTouchDelegate(id, id, id, id)
}

@Suppress("unused")
fun View.getBitmap(): Bitmap {
    val width = this.measuredWidth
    val height = this.measuredHeight
    val b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val c = Canvas(b)
    this.layout(0, 0, this.layoutParams.width, this.layoutParams.height)
    this.draw(c)
    return b
}
