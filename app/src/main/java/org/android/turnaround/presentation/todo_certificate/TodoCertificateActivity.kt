package org.android.turnaround.presentation.todo_certificate

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import org.android.turnaround.R
import org.android.turnaround.databinding.ActivityTodoCertificateBinding
import org.android.turnaround.presentation.todo_guide.TodoGuideActivity
import org.android.turnaround.util.binding.BindingActivity
import org.android.turnaround.util.checkCameraPermission
import org.android.turnaround.util.checkCameraPermissionUnderQ
import org.android.turnaround.util.dialog.DialogBtnClickListener
import org.android.turnaround.util.dialog.WarningDialogFragment
import org.android.turnaround.util.dialog.WarningType
import org.android.turnaround.util.getImgUri
import org.android.turnaround.util.getPathFromUri
import timber.log.Timber
import java.io.File

class TodoCertificateActivity : BindingActivity<ActivityTodoCertificateBinding>(R.layout.activity_todo_certificate) {
    private var imgUri: Uri? = null
    private val fromCameraActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        imgUri?.let { uri ->
            Thread.sleep(700)
            if (File(getPathFromUri(this, uri)).exists()) {
                binding.imgUri = uri.toString()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.imgUri = intent.getStringExtra(TodoGuideActivity.IMG_URI)
        initTakePhotoAgainBtnClickListener()
    }

    private fun initTakePhotoAgainBtnClickListener() {
        binding.btnTodoCertificateTakePhotoAgain.setOnClickListener {
            WarningDialogFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(
                        WarningDialogFragment.WARNING_TYPE,
                        WarningType.WARNING_TAKE_PHOTO_AGAIN
                    )
                    putParcelable(
                        WarningDialogFragment.CONFIRM_ACTION,
                        DialogBtnClickListener(clickAction = { takePictureAgain() })
                    )
                }
            }.show(supportFragmentManager, WarningDialogFragment.DIALOG_WARNING)
        }
    }

    private fun takePictureAgain() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (checkCameraPermission(this)) {
                takePicture()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.CAMERA),
                    TodoGuideActivity.REQUEST_CAMERA_PERMISSION
                )
            }
        } else {
            if (checkCameraPermissionUnderQ(this)) {
                takePicture()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    TodoGuideActivity.REQUEST_CAMERA_PERMISSION_UNDER_Q
                )
            }
        }
    }

    private fun takePicture() {
        try {
            imgUri = getImgUri(contentResolver)
                ?: throw NullPointerException()
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
                it.putExtra(MediaStore.EXTRA_OUTPUT, imgUri)
                fromCameraActivityLauncher.launch(it)
            }
        } catch (e: NullPointerException) {
            Timber.e(getString(R.string.null_img_uri))
        }
    }
}
