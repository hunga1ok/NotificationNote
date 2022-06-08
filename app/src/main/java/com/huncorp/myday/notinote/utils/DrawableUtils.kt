package com.huncorp.myday.notinote.utils

import android.content.ContentValues
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.TypedValue
import androidx.annotation.ColorInt
import com.huncorp.myday.notinote.R
import java.io.*

object DrawableUtils {

    fun stroke(@ColorInt color: Int, strokeSize: Int = 1, radius: Int = 4): Drawable {
        val gd = GradientDrawable()
        gd.setColor(Color.TRANSPARENT)
        gd.setStroke(strokeSize.dp, color)
        gd.cornerRadius = radius.toFloat().dp
        return gd
    }

    fun fullRounded(@ColorInt color: Int, radius: Int = 4): Drawable {
        val gd = GradientDrawable()
        gd.setColor(color)
        gd.cornerRadius = radius.toFloat().dp
        return gd
    }

    fun partialRounded(
        @ColorInt color: Int,
        topLeft: Int = 0,
        topRight: Int = 0,
        bottomRight: Int = 0,
        bottomLeft: Int = 0
    ): Drawable {
        val gd = GradientDrawable()
        gd.setColor(color)
        gd.cornerRadii = floatArrayOf(
            topLeft.toFloat().dp,
            topLeft.toFloat().dp,
            topRight.toFloat().dp,
            topRight.toFloat().dp,
            bottomRight.toFloat().dp,
            bottomRight.toFloat().dp,
            bottomLeft.toFloat().dp,
            bottomLeft.toFloat().dp
        )
        return gd
    }
}

val Float.dp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

fun Bitmap.saveToGallery(context: Context): Uri? {
    val albumName = context.getString(R.string.app_name)
    val filename = "$albumName-${System.currentTimeMillis()}.png"
    val write: (OutputStream) -> Boolean = {
        this.setHasAlpha(true)
        this.compress(Bitmap.CompressFormat.PNG, 100, it)
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            put(
                MediaStore.MediaColumns.RELATIVE_PATH,
                "${Environment.DIRECTORY_DCIM}/$albumName"
            )
        }

        return context.contentResolver.let {
            it.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)?.let { uri ->
                it.openOutputStream(uri)?.let(write)
                return uri
            }
        }
    } else {
        val imagesDir =
            Environment.getExternalStorageDirectory().toString() + File.separator + albumName
        val file = File(imagesDir)
        if (!file.exists()) {
            file.mkdir()
        }
        val image = File(imagesDir, filename)
        write(FileOutputStream(image))
        return Uri.fromFile(image)
    }
}

fun Bitmap.saveToCached(context: Context, fileName: String = "image-face.png"): File? {
    //get cache directory
    val cachePath = File(context.externalCacheDir, "FaceRecognition/")
    cachePath.mkdirs()

    //create png file
    val file = File(cachePath, fileName)
    val fileOutputStream: FileOutputStream
    return try {
        fileOutputStream = FileOutputStream(file)
        this.setHasAlpha(true)
        this.compress(
            Bitmap.CompressFormat.PNG,
            100,
            fileOutputStream
        )
        fileOutputStream.flush()
        fileOutputStream.close()
        file
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun Array<Float>.saveToCached(context: Context, fileName: String = "image-face.txt"): File? {
    //get cache directory
    val cachePath = File(context.externalCacheDir, "FaceRecognition/")
    cachePath.mkdirs()

    //create png file
    val file = File(cachePath, fileName)
    val output: FileWriter
    return try {
        output = FileWriter(file)
        output.write("[")
        for (i in 0 until this.size - 1) {
            output.write(this[i].toString() + ",")
        }
        output.write(this[this.size - 1].toString() + "]")
        output.close()
        file
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}