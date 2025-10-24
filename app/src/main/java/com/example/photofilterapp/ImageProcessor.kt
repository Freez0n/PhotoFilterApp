package com.example.photofilterapp

import android.graphics.*

object ImageProcessor {

    private fun getSafeConfig(src: Bitmap): Bitmap.Config {
        return src.config ?: Bitmap.Config.ARGB_8888
    }

    fun applyGrayScale(src: Bitmap): Bitmap {
        val bmp = Bitmap.createBitmap(src.width, src.height, getSafeConfig(src))
        val canvas = Canvas(bmp)
        val paint = Paint()
        val colorMatrix = ColorMatrix().apply { setSaturation(0f) }
        paint.colorFilter = ColorMatrixColorFilter(colorMatrix)
        canvas.drawBitmap(src, 0f, 0f, paint)
        return bmp
    }

    fun applySepia(src: Bitmap): Bitmap {
        val bmp = Bitmap.createBitmap(src.width, src.height, getSafeConfig(src))
        val canvas = Canvas(bmp)
        val paint = Paint()
        val sepiaMatrix = ColorMatrix().apply {
            setSaturation(0f)
            postConcat(ColorMatrix().apply { setScale(1f, 1f, 0.8f, 1f) })
        }
        paint.colorFilter = ColorMatrixColorFilter(sepiaMatrix)
        canvas.drawBitmap(src, 0f, 0f, paint)
        return bmp
    }

    fun applyInvert(src: Bitmap): Bitmap {
        val bmp = Bitmap.createBitmap(src.width, src.height, getSafeConfig(src))
        val canvas = Canvas(bmp)
        val paint = Paint()
        val colorMatrix = ColorMatrix(
            floatArrayOf(
                -1f, 0f, 0f, 0f, 255f,
                0f, -1f, 0f, 0f, 255f,
                0f, 0f, -1f, 0f, 255f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        paint.colorFilter = ColorMatrixColorFilter(colorMatrix)
        canvas.drawBitmap(src, 0f, 0f, paint)
        return bmp
    }

    fun applyHighContrast(src: Bitmap): Bitmap {
        val bmp = Bitmap.createBitmap(src.width, src.height, getSafeConfig(src))
        val canvas = Canvas(bmp)
        val paint = Paint()
        val contrast = 1.5f
        val scale = contrast
        val translate = (-.5f * scale + .5f) * 255f
        val cm = ColorMatrix(
            floatArrayOf(
                scale, 0f, 0f, 0f, translate,
                0f, scale, 0f, 0f, translate,
                0f, 0f, scale, 0f, translate,
                0f, 0f, 0f, 1f, 0f
            )
        )
        paint.colorFilter = ColorMatrixColorFilter(cm)
        canvas.drawBitmap(src, 0f, 0f, paint)
        return bmp
    }

    fun applyBlur(src: Bitmap): Bitmap {
        val scale = 0.1f
        val width = (src.width * scale).toInt()
        val height = (src.height * scale).toInt()
        val small = Bitmap.createScaledBitmap(src, width, height, true)
        return Bitmap.createScaledBitmap(small, src.width, src.height, true)
    }

    fun applyBrightness(src: Bitmap): Bitmap {
        val bmp = Bitmap.createBitmap(src.width, src.height, getSafeConfig(src))
        val canvas = Canvas(bmp)
        val paint = Paint()
        val cm = ColorMatrix(
            floatArrayOf(
                1f, 0f, 0f, 0f, 30f,
                0f, 1f, 0f, 0f, 30f,
                0f, 0f, 1f, 0f, 30f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        paint.colorFilter = ColorMatrixColorFilter(cm)
        canvas.drawBitmap(src, 0f, 0f, paint)
        return bmp
    }

    fun applySaturate(src: Bitmap): Bitmap {
        val bmp = Bitmap.createBitmap(src.width, src.height, getSafeConfig(src))
        val canvas = Canvas(bmp)
        val paint = Paint()
        val cm = ColorMatrix().apply { setSaturation(2f) }
        paint.colorFilter = ColorMatrixColorFilter(cm)
        canvas.drawBitmap(src, 0f, 0f, paint)
        return bmp
    }

    fun applyVintage(src: Bitmap): Bitmap {
        val bmp = Bitmap.createBitmap(src.width, src.height, getSafeConfig(src))
        val canvas = Canvas(bmp)
        val paint = Paint()
        val cm = ColorMatrix().apply {
            setSaturation(0.6f)
            postConcat(ColorMatrix().apply { setScale(1f, 0.95f, 0.9f, 1f) })
        }
        paint.colorFilter = ColorMatrixColorFilter(cm)
        canvas.drawBitmap(src, 0f, 0f, paint)
        return bmp
    }
}
