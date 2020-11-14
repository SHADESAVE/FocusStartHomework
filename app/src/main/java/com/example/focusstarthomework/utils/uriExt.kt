package com.example.focusstarthomework.utils

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream


fun Uri.toBase64String(contentResolver: ContentResolver): String {
    val imageStream = contentResolver.openInputStream(this)
    val selectedImage = BitmapFactory.decodeStream(imageStream)
    val baos = ByteArrayOutputStream()
    selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val b = baos.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}