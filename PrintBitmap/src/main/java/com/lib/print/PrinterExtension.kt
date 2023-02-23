package com.lib.print

import android.graphics.*


/**
 * Created by Anonim date on 23/02/2023.
 * Bengkulu, Indonesia.
 * Copyright (c) Company. All rights reserved.
 **/
fun Bitmap.scaledBitmap(desiredWidth: Int = 400): Bitmap {
    val scale = desiredWidth.toFloat() / width.toFloat()
    val height = (height * scale).toInt()
    return Bitmap.createScaledBitmap(this, desiredWidth, height, false)
}

fun Bitmap.blackBitmap(): Bitmap {
    val bitmap = Bitmap.createBitmap(this)
    val canvas = Canvas(bitmap)
    val ma = ColorMatrix()
    ma.setSaturation(0f)
    val paint = Paint()
    paint.colorFilter = ColorMatrixColorFilter(ma)
    canvas.drawBitmap(bitmap, 0f, 0f, paint)
    return bitmap
}

fun Bitmap.replaceColor(): Bitmap {
    val width = this.width
    val height = this.height
    val pixels = IntArray(width * height)
    this.getPixels(pixels, 0, 1 * width, 0, 0, width, height)
    for (x in pixels.indices) {
        if (pixels[x] != Color.TRANSPARENT) pixels[x] = Color.BLACK
    }
    return Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888)
}

fun Bitmap.replaceColorTransparent(): Bitmap {
    val width = this.width
    val height = this.height
    val pixels = IntArray(width * height)
    this.getPixels(pixels, 0, 1 * width, 0, 0, width, height)
    for (x in pixels.indices) {
        if (pixels[x] == Color.WHITE) pixels[x] = Color.TRANSPARENT
    }
    return Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888)
}

fun Int.feed() : Int = this * 12

