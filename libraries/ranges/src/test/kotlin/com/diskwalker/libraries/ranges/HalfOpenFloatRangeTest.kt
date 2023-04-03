package com.diskwalker.libraries.ranges

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class HalfOpenFloatRangeTest {

    private val range = 3f until 5f

    @Test
    fun creation() {
        range.start shouldBe 3f
        range.end shouldBe 5f
    }

    @Test
    fun inclusion() {
        (3f in range) shouldBe true
        (3.5f in range) shouldBe true
        (4.999f in range) shouldBe true
        (5f in range) shouldBe false
    }
}
