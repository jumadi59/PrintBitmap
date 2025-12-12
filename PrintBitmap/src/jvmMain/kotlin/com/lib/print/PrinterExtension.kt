package com.lib.print

import com.lib.print.Print.Companion.FEED_SIZE
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.InputStream
import javax.imageio.ImageIO

fun BufferedImage.scaledBitmap(desiredWidth: Int = 400): BufferedImage {
    val scale = desiredWidth.toFloat() / width.toFloat()
    val height = (height * scale).toInt()
    val output = BufferedImage(desiredWidth, height, BufferedImage.TYPE_INT_ARGB)
    val g: Graphics2D = output.createGraphics()
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
    g.drawImage(this, 0, 0, desiredWidth, height, null)
    g.dispose()
    return output
}

fun BufferedImage.blackBitmap(): BufferedImage {
    val output = BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY)
    val g = output.createGraphics()
    g.drawImage(this, 0, 0, null)
    g.dispose()
    return output
}

fun BufferedImage.replaceColor(): BufferedImage {
    val output = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    for (x in 0 until width) {
        for (y in 0 until height) {
            val pixel = getRGB(x, y)
            if (pixel ushr 24 != 0x00) {
                output.setRGB(x, y, Color.BLACK.rgb)
            }
        }
    }
    return output
}

fun BufferedImage.replaceColorTransparent(): BufferedImage {
    val output = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    for (x in 0 until width) {
        for (y in 0 until height) {
            val pixel = getRGB(x, y)
            if (pixel == Color.WHITE.rgb) {
                output.setRGB(x, y, 0x00000000)
            } else {
                output.setRGB(x, y, pixel)
            }
        }
    }
    return output
}

fun Int.feed(): Int = this * FEED_SIZE

fun font(resourcePath: String): Font {
    val stream: InputStream = requireNotNull(Thread.currentThread().contextClassLoader.getResourceAsStream(resourcePath)) {
        "Font resource $resourcePath not found"
    }
    return Font.createFont(Font.TRUETYPE_FONT, stream)
}

fun BufferedImage.asPng(): ByteArray {
    val output = java.io.ByteArrayOutputStream()
    ImageIO.write(this, "png", output)
    return output.toByteArray()
}
