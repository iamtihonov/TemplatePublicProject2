package ua.good.utils

import android.text.TextUtils
import android.util.Patterns

/**
 * Помощник для класса String
 */
fun String.isEmailValid(): Boolean {
    return !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
