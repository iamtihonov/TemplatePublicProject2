@file:Suppress("unused")

package ua.good.utils.base

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ProcessLifecycleOwner
import ua.good.utils.logs.logLifecycle

abstract class BaseService : Service() {
    init {
        logLifecycle("init")
    }

    protected fun endForeground() {
        stopForeground(true)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    protected fun appIsVisible(): Boolean {
        return ProcessLifecycleOwner
            .get()
            .lifecycle
            .currentState
            .isAtLeast(Lifecycle.State.STARTED)
    }

    protected fun finalize() {
        logLifecycle("finalize")
    }
}
