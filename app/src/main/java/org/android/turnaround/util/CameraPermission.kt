package org.android.turnaround.util

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun checkCameraPermission(context: Context) =
    ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

fun checkCameraPermissionUnderQ(context: Context) =
    checkCameraPermission(context) && ContextCompat.checkSelfPermission(
        context,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED
