package ua.artem.template.utils

import ua.good.utils.base.BaseUtil
import java.math.BigDecimal

/**
 * Класс помогающий работать типами с плавающей точкой, если его не использовать, результат будет
 * не точен
 */
abstract class IMathUtil : BaseUtil() {
    abstract fun roundFloat(value1: Float): Float
    abstract fun addFloatValues(rounded: Boolean, roundScale: Int, vararg values: Float): Float
    abstract fun subtractTwoFloat(value1: Float, value2: Float): Float
    abstract fun multiplyFloatValues(vararg values: Float): Float
    abstract fun divideTwoFloat(vararg values: Float): Float
}

@Suppress("unused")
class MathUtil : IMathUtil() {

    override fun roundFloat(value1: Float): Float {
        val value1D = BigDecimal(value1.toString()).setScale(2, BigDecimal.ROUND_HALF_UP)
        return value1D.toFloat()
    }

    private fun addFloatValues(vararg values: Float): Float {
        if (values.size < MIN_VALUES_LENGTH) {
            throw NumberFormatException()
        }

        var result = BigDecimal(values[0].toString())
        for (i in 1 until values.size) {
            val nextValue = BigDecimal(values[i].toString())
            result = result.add(nextValue)
        }

        return result.toFloat()
    }

    override fun addFloatValues(rounded: Boolean, roundScale: Int, vararg values: Float): Float {
        var result = BigDecimal(addFloatValues(*values).toString())
        if (rounded) {
            result = result.setScale(roundScale, BigDecimal.ROUND_HALF_UP)
        }

        return result.toFloat()
    }

    override fun subtractTwoFloat(value1: Float, value2: Float): Float {
        val value1D = BigDecimal(value1.toString())
        val value2D = BigDecimal(value2.toString())
        val result = value1D.subtract(value2D)

        return result.toFloat()
    }

    override fun multiplyFloatValues(vararg values: Float): Float {
        if (values.size < MIN_VALUES_LENGTH) {
            throw NumberFormatException()
        }

        var result = BigDecimal(values[0].toString())
        for (i in 1 until values.size) {
            val nextValue = BigDecimal(values[i].toString())
            result = result.multiply(nextValue)
        }

        return result.toFloat()
    }

    override fun divideTwoFloat(vararg values: Float): Float {
        if (values.size < MIN_VALUES_LENGTH) {
            throw NumberFormatException()
        }

        var result = BigDecimal(values[0].toString())
        for (i in 1 until values.size) {
            val nextValue = BigDecimal(values[i].toString())
            result = result.divide(nextValue, DIVIDE_SCALE, BigDecimal.ROUND_HALF_UP)
        }

        return result.toFloat()
    }

    companion object {
        private const val MIN_VALUES_LENGTH = 2
        private const val DIVIDE_SCALE = 4
    }
}
