package com.lib.print.sample

import com.lib.print.buildPngContent
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestPrintLayoutTest {

    @Test
    fun `builds layout without errors`() {
        val print = testPrintLayout()
        val image = print.build()
        assertTrue(image.width > 0 && image.height > 0)
    }

    @Test
    fun `png content has bytes`() {
        val content = testPrintLayout().buildPngContent()
        assertEquals("image/png", content.contentType?.toString())
        assertTrue(content.bytes().isNotEmpty())
    }
}
