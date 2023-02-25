package com.lib.print

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat
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
            .add(PrintText("1234********9456", fontStyle = FontStyle.BOLD))
            .add(PrintText("Jumadi Janjaya", fontStyle = FontStyle.BOLD))
            .add(PrintText("SETTLED", fontStyle = FontStyle.BOLD))
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
                PrintTextFont(resources.font(R.font.mynerve_regular),"TOTAL  :", FontSize.LARGE, fontStyle = FontStyle.BOLD),
                PrintTextFont(resources.font(R.font.mynerve_regular),"RP. 200.000", FontSize.LARGE, align = Align.RIGHT, fontStyle = FontStyle.BOLD),
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

    private fun Resources.font(@FontRes resId: Int) : Typeface {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getFont(resId)
        } else {
            ResourcesCompat.getFont(this@MainActivity, resId)?:Typeface.SERIF
        }
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