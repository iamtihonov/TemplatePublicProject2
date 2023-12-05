package ua.artem.template.application.extensions

import androidx.viewpager.widget.ViewPager

@Suppress("unused")
fun ViewPager.onPageSelected(listener: (Int) -> Unit) {
    this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
            // Реализация не нужна
        }

        override fun onPageScrolled(p1: Int, p2: Float, p3: Int) {
            // Реализация не нужна
        }

        override fun onPageSelected(position: Int) {
            listener.invoke(position)
        }
    })
}
