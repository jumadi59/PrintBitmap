package com.lib.print

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.lib.print.component.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.receipt_card).setOnClickListener {
            val bitmap = testPrintLayout((resources.getDrawable(R.drawable.logo_cashlez, theme) as BitmapDrawable).bitmap.replaceColor())
            findViewById<ImageView>(R.id.result_img).setImageBitmap(bitmap)
        }

    }

    private fun testPrintLayout(logo: Bitmap): Bitmap {
        return Print().addHeader(logo)
            .add(
                LayoutAbsolute(childs = arrayListOf(
                PrintText("TID: 123456"),
                PrintText("MID: 123456789012", align = Align.RIGHT),
            ))
            )
            .add(PrintText("CARD TYPE: OFF-US GPN (DIP)"))
            .add(PrintText("1234********9456", isBold = true))
            .add(PrintText("Jumadi Janjaya", isBold = true))
            .add(PrintText("SETTLED", isBold = true))
            .feed()
            .add(LayoutAbsolute(childs = arrayListOf(
                PrintText("REF NO: 123456789012"),
                PrintText("MAPPR: 123456", align = Align.RIGHT),
            )))
            .add(LayoutAbsolute(childs = arrayListOf(
                PrintText("BATCH  : 123456789012"),
                PrintText("INVOICE: 123456", align = Align.RIGHT),
            )))
            .add(PrintText("DATETIME: 22 FEB 23/01:15:18(GMT+07:00)"))
            .feed(3.feed())
            .add(LayoutAbsolute(childs = arrayListOf(
                PrintText("TOTAL  :", FontSize.LARGE.size, isBold = true),
                PrintText("RP. 200.000", FontSize.LARGE.size, align = Align.RIGHT, isBold = true),
            )))
            .add(PrintText("TC:  123456789123"))
            .feed(2.feed())
            .add(PrintText("NO SIGNATURE REQUESTED", align = Align.CENTER))
            .add(PrintText("*** PIN VERIFICATION SUCCESS ***", align = Align.CENTER))
            .feed().addFooter().build()
    }


    private fun Print.addHeader(logo: Bitmap) :Print {
        add(PrintImage(logo, Align.CENTER)).feed()
        add(PrintText("TOko Jaya", align = Align.CENTER))
        add(PrintText("Central Park", align = Align.CENTER))
        add(PrintText("Jl Gatot Subroto Kav 12", align = Align.CENTER))
        add(PrintText("Jakarta Barat", align = Align.CENTER))
        feed()
        return this
    }

    private fun Print.addFooter() : Print {
        singleLine()
        add(PrintText("I agree to pay the total amount above and I have", FontSize.SMALL.size, align = Align.CENTER))
        add(PrintText("recelved the goods and/or aervloea moordlngly", FontSize.SMALL.size, align = Align.CENTER))
        singleLine()
        feed(3.feed())
        add(PrintText("Powered by Cashlez", align = Align.CENTER))
        feed(4.feed())

        return this
    }
}