package com.diskusage.core

/**
 * Represents a half-open range of floating point numbers.
 * Includes [start] but excludes [end] values.
 * @see ClosedFloatingPointRange
 */
data class HalfOpenFloatRange(
    val start: Float,
    val end: Float,
) {
    /**
     * Checks if [provided number][other] is greater than or equal than [start] and less than [end].
     *
     * If the [end] value is less than or equal to [start] value than [provided number][other] will never be contained within the range.
     */
    operator fun contains(other: Float) = start <= other && other < end
}

/**
 * Creates [HalfOpenFloatRange] from `this` value up to but excluding the specified [to] value.
 */
infix fun Float.until(to: Float): HalfOpenFloatRange {
    return HalfOpenFloatRange(this, to)
}
