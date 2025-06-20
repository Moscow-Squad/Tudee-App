package com.moscow.tudee.presentation.util

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import java.io.File

fun Context.saveUriToInternalStorage(sourceUri: Uri): Uri? {
    val input = contentResolver.openInputStream(sourceUri) ?: return null
    val iconsDir = File(filesDir, "category_icons")
    if (!iconsDir.exists()) iconsDir.mkdirs()
    val mime = contentResolver.getType(sourceUri)
    val ext  = mime?.substringAfterLast('/') ?: "jpg"
    val dest = File(iconsDir, "${System.currentTimeMillis()}.$ext")

    input.use { inp ->
        dest.outputStream().use { out ->
            inp.copyTo(out)
        }
    }

    return dest.toUri()
}
