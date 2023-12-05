package ua.good.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.AnyRes
import androidx.annotation.ArrayRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.Px
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import ua.good.utils.base.BaseUtil
import ua.good.utils.logs.logError

/**
 * В данный момент создается внутри View, так как из них не получить ProjectApplication.
 * Плюсы класса:
 * 1. Единственная утилита для доступа к ресурсам.
 * 2. Обработка ошибок.
 */
abstract class IResourcesUtil : BaseUtil() {
    @Px
    abstract fun getDimensionPixelSize(@DimenRes id: Int): Int
    abstract fun getDrawable(@DrawableRes id: Int): Drawable?
    abstract fun getString(@StringRes id: Int): String
    abstract fun getStringArray(@ArrayRes stringId: Int): Array<String>
    abstract fun getString(@StringRes id: Int, vararg formatArgs: Any): String

    @ColorInt
    abstract fun getColor(@ColorRes colorId: Int): Int
    abstract fun getFont(@FontRes id: Int): Typeface?
    abstract fun getResourceName(id: Int): String
}

class ResourcesUtil(val context: Context) : IResourcesUtil() {

    private var resources = context.resources

    @Px
    override fun getDimensionPixelSize(@DimenRes id: Int): Int {
        return try {
            return resources.getDimensionPixelSize(id)
        } catch (e: Resources.NotFoundException) {
            logError(e)
            0
        }
    }

    override fun getDrawable(@DrawableRes id: Int): Drawable? {
        return try {
            ResourcesCompat.getDrawable(resources, id, null)
        } catch (e: Resources.NotFoundException) {
            logError(e)
            null
        }
    }

    override fun getString(@StringRes id: Int): String {
        return try {
            resources.getString(id)
        } catch (e: Resources.NotFoundException) {
            logError(e)
            ""
        }
    }

    override fun getStringArray(@ArrayRes stringId: Int): Array<String> {
        return resources.getStringArray(stringId)
    }

    /**
     * Не работает
     */
    override fun getString(@StringRes id: Int, vararg formatArgs: Any): String {
        return try {
            resources.getString(id, formatArgs)
        } catch (e: Resources.NotFoundException) {
            logError(e)
            ""
        }
    }

    @ColorInt
    override fun getColor(@ColorRes colorId: Int): Int {
        return try {
            ContextCompat.getColor(context, colorId)
        } catch (e: Resources.NotFoundException) {
            logError(e)
            Color.TRANSPARENT
        }
    }

    override fun getFont(@FontRes id: Int): Typeface? {
        return try {
            ResourcesCompat.getFont(context, id)
        } catch (e: Resources.NotFoundException) {
            logError(e)
            null
        }
    }

    override fun getResourceName(@AnyRes id: Int): String {
        return if (id == View.NO_ID) {
            "no_id"
        } else {
            resources.getResourceEntryName(id)
        }
    }
}
