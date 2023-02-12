package com.uxapp.oktava.file_worker.image

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import java.io.File


object ImageReader {
    fun readSafeBitmapFromFileAbsolutePathWithWidth(absolutePath: String?, width: Int): Bitmap? {
        val bmOptions = BitmapFactory.Options()
        return try{
            val bitmap = BitmapFactory.decodeFile(absolutePath, bmOptions) ?: return null
            val height = bitmap.height * width / bitmap.width
            Bitmap.createScaledBitmap(
                bitmap,
                width,
                height,
                true
            )
        } catch(e: IllegalArgumentException){
            null
        }
    }
    fun readSafeBitmapFromFileAbsolutePath(absolutePath: String?): Bitmap? {
        val bmOptions = BitmapFactory.Options()
        return try{
            return BitmapFactory.decodeFile(absolutePath, bmOptions)
        } catch(e: IllegalArgumentException){
            null
        }
    }
    fun readSafeBitmapFromFilepathWithWidth(path: String?, width: Int): Bitmap? {
        if(path == null) return null
        val sd: File = Environment.getExternalStorageDirectory()
        val imageFile = File(sd, path)
        return readSafeBitmapFromFileAbsolutePathWithWidth(imageFile.absolutePath, width)
    }
}