package com.diskwalker.libraries.formatters

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class FileSizeFormatterTest {

    @Nested
    inner class BinaryFormat {

        @Test
        fun `negative value`() {
            shouldThrow<IllegalArgumentException> {
                FileSizeFormatter.toBinaryFormat(-42)
            }
        }

        @Test
        fun `zero bytes`() {
            FileSizeFormatter.toBinaryFormat(0) shouldBe "0 Bytes"
        }

        @Test
        fun bytes() {
            FileSizeFormatter.toBinaryFormat(1023L) shouldBe "1023 Bytes"
        }

        @Test
        fun kibibytes() {
            FileSizeFormatter.toBinaryFormat(1024L) shouldBe "1 KiB"
        }

        @Test
        fun mebibytes() {
            FileSizeFormatter.toBinaryFormat(12_345L) shouldBe "12.06 KiB"
            FileSizeFormatter.toBinaryFormat(10_123_456L) shouldBe "9.65 MiB"
        }

        @Test
        fun gibibytes() {
            FileSizeFormatter.toBinaryFormat(10_123_456_798L) shouldBe "9.43 GiB"
        }

        @Test
        fun exbibytes() {
            FileSizeFormatter.toBinaryFormat(1_777_777_777_777_777_777L) shouldBe "1.54 EiB"
        }
    }

    @Nested
    inner class SIFormat {

        @Test
        fun `negative value`() {
            shouldThrow<IllegalArgumentException> {
                FileSizeFormatter.toSiFormat(-42)
            }
        }

        @Test
        fun `zero bytes`() {
            FileSizeFormatter.toSiFormat(0) shouldBe "0 Bytes"
        }

        @Test
        fun bytes() {
            FileSizeFormatter.toSiFormat(999L) shouldBe "999 Bytes"
        }

        @Test
        fun kilobytes() {
            FileSizeFormatter.toSiFormat(1000L) shouldBe "1 KB"
            FileSizeFormatter.toSiFormat(12_345L) shouldBe "12.35 KB"
        }

        @Test
        fun megabytes() {
            FileSizeFormatter.toSiFormat(10_123_456L) shouldBe "10.12 MB"
        }

        @Test
        fun gigabytes() {
            FileSizeFormatter.toSiFormat(10_123_456_798L) shouldBe "10.12 GB"
        }

        @Test
        fun exabytes() {
            FileSizeFormatter.toSiFormat(1_777_777_777_777_777_777L) shouldBe "1.78 EB"
        }
    }
}
