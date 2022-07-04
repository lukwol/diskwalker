package com.diskusage.domain.common

data class HalfOpenFloatRange(
    val start: Float,
    val end: Float,
) {
    operator fun contains(other: Float) = start <= other && other < end
}

infix fun Float.until(to: Float) = HalfOpenFloatRange(this, to)
