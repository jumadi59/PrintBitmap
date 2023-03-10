package com.lib.print.component

import android.graphics.*
import android.util.Base64
import com.lib.print.Print
import com.lib.print.scaledBitmap


/**
 * Created by Anonim date on 23/02/2023.
 * Bengkulu, Indonesia.
 * Copyright (c) Company. All rights reserved.
 **/
class PrintImage<out T>(private val image: T, align: Align = Align.LEFT, private val width: Int = 200) : BasePrint(align) {
    private var bitmapImage: Bitmap?= null
    private var padding = (4 * Print.scale).toInt()

    constructor(image: T, align: Align) : this(image, align, 200)

    constructor(image: T) : this(image, Align.LEFT)

    override fun height(): Int {
        return when (image) {
            is Bitmap -> {
                bitmapImage = image.scaledBitmap((width * Print.scale).toInt())
                bitmapImage!!.height + padding
            }
            is String -> {
                val decodedString: ByteArray = Base64.decode(image, Base64.DEFAULT)
                bitmapImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                bitmapImage = bitmapImage!!.scaledBitmap((width * Print.scale).toInt())
                bitmapImage!!.height + padding
            }
            else -> throw java.lang.Exception("format not found")
        }
    }

    override fun draw(canvas: Canvas, vector: Vector) {
        val left  = when(align) {
            Align.CENTER -> vector.x + ((vector.width - bitmapImage!!.width) / 2)
            Align.RIGHT -> vector.x + (vector.width - bitmapImage!!.width)
            else -> vector.x
        }
        canvas.drawBitmap(bitmapImage!!, left.toFloat(), vector.y.toFloat(), Paint())
    }
}