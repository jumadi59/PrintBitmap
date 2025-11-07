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
        return Print().config(2f).addHeader(logo)
            .add(
                LayoutAbsolute(children = arrayListOf(
                PrintText("TID: 123456"),
                PrintText("MID: 123456789012", align = Align.RIGHT),
            ))
            )
            .add(PrintText("CARD TYPE: OFF-US GPN (DIP)"))
            .add(PrintText("1234********9456", fontStyle = FontStyle.BOLD))
            .add(PrintText("Jumadi Janjaya", fontStyle = FontStyle.BOLD))
            .add(PrintText("SETTLED", fontStyle = FontStyle.BOLD))
            .feed()
            .add(LayoutFlex(children = arrayListOf(
                PrintText("REF NO: 123456789012"),
                PrintText("MAPPR: 123456", align = Align.RIGHT),
            ), flexs = floatArrayOf(0.6f, 0.4f)
            ))
            .add(LayoutAbsolute(children = arrayListOf(
                PrintText("BATCH  : 123456789012"),
                PrintText("INVOICE: 123456", align = Align.RIGHT),
            )))
            .add(LayoutFlex(children = arrayListOf(
                PrintText("DATETIME:"),
                PrintText("22 FEBRUARI 2023/01:15:18 (GMT+07:00)", align = Align.RIGHT)
            ), floatArrayOf(0.3f, 0.7f)
            ))
            .feed(3.feed())
            .add(LayoutAbsolute(children = arrayListOf(
                PrintTextFont(font(R.font.mynerve_regular),"TOTAL  :", FontSize.LARGE, fontStyle = FontStyle.BOLD),
                PrintTextFont(font(R.font.mynerve_regular),"RP. 200.000", FontSize.LARGE, align = Align.RIGHT, fontStyle = FontStyle.BOLD),
            )))
            .add(PrintText("TC:  123456789123"))
            .feed(2.feed())
            .add(PrintText("NO SIGNATURE REQUESTED", align = Align.CENTER))
            .add(PrintText("*** PIN VERIFICATION SUCCESS ***", align = Align.CENTER))
            .add(PrintText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim", align = Align.CENTER))
            .feed().addFooter().build()
    }


    private fun Print.addHeader(logo: Bitmap) :Print {
        add(PrintImage(logo, Align.CENTER)).feed()
        add(PrintText("Toko Jaya\nCentral Park\nJl Gatot Subroto Kav 12\nJakarta Barat", align = Align.CENTER))
        feed()
        return this
    }

    private fun Print.addFooter() : Print {
        doubleLineLine()
        add(PrintText("I agree to pay the total amount above and I have\nrecelved the goods and/or aervloea moordlngly", FontSize.SMALL.size, align = Align.CENTER))
        singleLine()
        feed(3.feed())
        add(PrintText("Powered by Cashlez", align = Align.CENTER))
        feed(4.feed())

        return this
    }
}