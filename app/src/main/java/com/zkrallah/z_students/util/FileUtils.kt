package com.zkrallah.z_students.util


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


fun getImageFileFromRealPath(realPath: String?): File? {
    if (realPath == null) return null
    val file = File(realPath)
    return if (file.exists()) file else null
}

fun cacheImageToFile(context: Context, uri: Uri): String? {
    try {
        // Open the input stream for the selected image
        val imageStream = context.contentResolver.openInputStream(uri)
        val selectedImage = BitmapFactory.decodeStream(imageStream)

        // Compress the bitmap and convert it to a byte array
        val baos = ByteArrayOutputStream()
        selectedImage.compress(Bitmap.CompressFormat.JPEG, 70, baos)
        val data = baos.toByteArray()

        // Determine the directory where you want to save the cached image
        val cacheDir = context.externalCacheDir // Change this if needed

        // Create a unique filename for the image
        val fileName = "cached_image_${System.currentTimeMillis()}.jpg"

        // Create a new file in the cache directory and write the data to it
        val cachedFile = File(cacheDir, fileName)
        val fileOutputStream = FileOutputStream(cachedFile)
        fileOutputStream.write(data)
        fileOutputStream.flush()
        fileOutputStream.close()

        // Return the path of the saved file
        return cachedFile.absolutePath
    } catch (e: IOException) {
        e.printStackTrace()
    }

    // Return null if there was an error saving the image
    return null
}