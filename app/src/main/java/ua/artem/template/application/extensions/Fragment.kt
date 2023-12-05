package ua.artem.template.application.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Использовать для отложенного выполнения кода внутри Activity или фрагмента
 */
@Suppress("unused")
fun Fragment.delayActionSafe(delayMillis: Long, action: () -> Unit): Job? {
    view ?: return null
    return viewLifecycleOwner.lifecycleScope.launch {
        delay(delayMillis)
        action()
    }
}
