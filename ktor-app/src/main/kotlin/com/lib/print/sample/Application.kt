package com.lib.print.sample

import com.lib.print.Print
import com.lib.print.buildPngContent
import com.lib.print.component.Align
import com.lib.print.component.FontSize
import com.lib.print.component.FontStyle
import com.lib.print.component.LayoutAbsolute
import com.lib.print.component.LayoutFlex
import com.lib.print.component.LayoutKeyValue
import com.lib.print.component.PrintText
import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        routing {
            get("/") {
                call.respondText("PrintBitmap ktor sample is running. Visit /testPrintLayout for a PNG preview.")
            }
            get("/testPrintLayout") {
                call.respond(testPrintLayout().buildPngContent())
            }
        }
    }.start(wait = true)
}

fun testPrintLayout(): Print {
    val flex3 = floatArrayOf(3f, 1f, 6f)
    val bold = FontStyle.BOLD

    return Print().apply {
        add(PrintText("PRINT BITMAP SAMPLE", FontSize.LARGE, align = Align.CENTER, fontStyle = bold))
        feed(2.feed())

        add(
            LayoutKeyValue(
                rows = listOf(
                    "Order" to "INV-12345",
                    "Date" to "01 Jan 2024",
                    "Time" to "12:30:00",
                    "Cashier" to "Jumadi",
                ),
            ),
        )

        feed()

        add(
            LayoutFlex(
                children = arrayListOf(
                    PrintText("ITEM"),
                    PrintText("QTY", align = Align.CENTER, fontStyle = bold),
                    PrintText("SUBTOTAL", align = Align.RIGHT, fontStyle = bold),
                ),
                flexes = flex3,
            ),
        )

        singleLine(true)

        add(
            LayoutFlex(
                children = arrayListOf(
                    PrintText("Coffee Beans"),
                    PrintText("2", align = Align.CENTER),
                    PrintText("Rp. 140.000", align = Align.RIGHT),
                ),
                flexes = flex3,
            ),
        )

        add(
            LayoutFlex(
                children = arrayListOf(
                    PrintText("Milk"),
                    PrintText("1", align = Align.CENTER),
                    PrintText("Rp. 30.000", align = Align.RIGHT),
                ),
                flexes = flex3,
            ),
        )

        feed()

        add(
            LayoutAbsolute(
                children = arrayListOf(
                    PrintText("TOTAL", FontSize.LARGE, fontStyle = bold),
                    PrintText("RP. 170.000", FontSize.LARGE, align = Align.RIGHT, fontStyle = bold),
                ),
            ),
        )

        feed(2.feed())
        add(PrintText("Thank you!", align = Align.CENTER))
        feed(2.feed())
    }
}
