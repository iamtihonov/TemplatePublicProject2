package ua.artem.template.base

import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.view.KeyEvent
import android.view.MenuItem
import androidx.activity.ComponentActivity
import ua.good.utils.logs.logLifecycle
import ua.good.utils.logs.logUi

/**
 * Базовая активность.
 */
@Suppress("TooManyFunctions")
abstract class BaseActivity : ComponentActivity(), BaseView, DialogInterface.OnDismissListener {

    init {
        logLifecycle("init")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        logLifecycle("onAttachedToWindow")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logLifecycle("onCreate")
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        logLifecycle("onCreate")
    }

    override fun onStart() {
        super.onStart()
        logLifecycle("onStart")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        logLifecycle("onRestoreInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        logLifecycle("onRestoreInstanceState")
    }

    override fun onResume() {
        super.onResume()
        logLifecycle("onResume")
        logUi("onResume()")
        intent.extras?.keySet()?.forEach {
            val value = intent.extras?.get(it)
            logUi("key = $it, value = $value")
        }
    }

    /**
     * Обычно не должен использоваться
     */
    override fun onPostResume() {
        super.onPostResume()
        logLifecycle("onPostResume")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        logLifecycle("onNewIntent")
    }

    /**
     * Выл нажат элемент menu
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        logUi("onOptionsItemSelected() = ${item.title}")
        return super.onOptionsItemSelected(item)
    }

    override fun onKeyLongPress(keyCode: Int, event: KeyEvent?): Boolean {
        logUi("onKeyLongPress(), keyCode = $keyCode")
        return super.onKeyLongPress(keyCode, event)
    }

    override fun onRestart() {
        super.onRestart()
        logLifecycle("onRestart")
    }

    /**
     * Вызывается только в том случае, если у приложения запрещен перезапуск Activity
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        logLifecycle("onConfigurationChanged $newConfig")
    }

    override fun onDismiss(dialog: DialogInterface?) {
        logUi("dialog onDismiss()")
    }

    override fun onPause() {
        super.onPause()
        logLifecycle("onPause")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        logLifecycle("onSaveInstanceState")
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        logLifecycle("onSaveInstanceState")
    }

    override fun onStop() {
        super.onStop()
        logLifecycle("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        logLifecycle("onDestroy")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        logLifecycle("onDetachedFromWindow")
    }

    protected fun finalize() {
        logLifecycle("finalize")
    }
}
