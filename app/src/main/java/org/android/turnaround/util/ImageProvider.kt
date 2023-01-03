package org.android.turnaround.util

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

fun getImgUri(contentResolver: ContentResolver): Uri? {
    val folderName = "TurnAround"
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val picturePath = "${Environment.DIRECTORY_PICTURES}${File.separator}$folderName"
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, "$timeStamp.jpeg")
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.MediaColumns.RELATIVE_PATH, picturePath)
        }
    }

    return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
}

fun getPathFromUri(context: Context, uri: Uri): String {
    val cursor: Cursor = context.contentResolver.query(uri, null, null, null, null)
        ?: throw NullPointerException()
    cursor.moveToNext()
    val columnIndex = cursor.getColumnIndex("_data")
    val path = if (columnIndex >= 0) {
        cursor.getString(columnIndex)
    } else {
        throw IllegalAccessException()
    }
    cursor.close()
    return path
}
