package com.lib.print.component

import com.lib.print.Print
import com.lib.print.scaledBitmap
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.util.Base64
import javax.imageio.ImageIO

class PrintImage<out T>(private val image: T, align: Align = Align.LEFT, private val width: Int = 200) : BasePrint(align) {
    private var bufferedImage: BufferedImage? = null
    private var padding = (4 * Print.scale).toInt()

    constructor(image: T, align: Align) : this(image, align, 200)
    constructor(image: T) : this(image, Align.LEFT)

    override fun height(): Int {
        return when (image) {
            is BufferedImage -> {
                bufferedImage = image.scaledBitmap((width * Print.scale).toInt())
                bufferedImage!!.height + padding
            }
            is String -> {
                val decodedString: ByteArray = Base64.getDecoder().decode(image)
                bufferedImage = ImageIO.read(ByteArrayInputStream(decodedString))
                bufferedImage = bufferedImage!!.scaledBitmap((width * Print.scale).toInt())
                bufferedImage!!.height + padding
            }
            else -> throw Exception("format not found")
        }
    }

    override fun draw(canvas: Graphics2D, vector: Vector) {
        val left = when (align) {
            Align.CENTER -> vector.x + ((vector.width - bufferedImage!!.width) / 2)
            Align.RIGHT -> vector.x + (vector.width - bufferedImage!!.width)
            else -> vector.x
        }
        canvas.drawImage(bufferedImage, left, vector.y, null)
    }
}
