package org.android.turnaround.presentation.todo_certificate

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.ActivityTodoCertificateBinding
import org.android.turnaround.presentation.todo_guide.TodoGuideActivity
import org.android.turnaround.presentation.todo_guide.TodoGuideActivity.Companion.IMG_URI
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

@AndroidEntryPoint
class TodoCertificateActivity : BindingActivity<ActivityTodoCertificateBinding>(R.layout.activity_todo_certificate) {
    private val viewModel by viewModels<TodoCertificateViewModel>()
    private var imgUri: Uri? = null
    private val fromCameraActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        imgUri?.let { uri ->
            Thread.sleep(700)
            if (File(getPathFromUri(this, uri)).exists()) {
                viewModel.initImgUri(uri)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        viewModel.initTodoId(intent.getIntExtra(TodoGuideActivity.TODO_GUIDE_TODO_ID, -1))
        intent.getStringExtra(IMG_URI)?.let { uri ->
            viewModel.initImgUri(Uri.parse(uri))
        }
        intent.removeExtra(IMG_URI)
        savedInstanceState?.let {
            imgUri = it.getParcelable(NEW_IMG_URI)
        }
        initCloseBtnClickListener()
        initCloseToolTipBtnClickListener()
        initTakePhotoAgainBtnClickListener()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(NEW_IMG_URI, imgUri)
    }

    private fun initCloseBtnClickListener() {
        binding.btnTodoCertificateClose.setOnClickListener {
            WarningDialogFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(
                        WarningDialogFragment.WARNING_TYPE,
                        WarningType.WARNING_CANCEL_ACTIVITY
                    )
                    putParcelable(
                        WarningDialogFragment.CONFIRM_ACTION,
                        DialogBtnClickListener(clickAction = { finish() })
                    )
                }
            }.show(supportFragmentManager, WarningDialogFragment.DIALOG_WARNING)
        }
    }

    private fun initCloseToolTipBtnClickListener() {
        binding.btnTodoCertificateCloseToolTip.setOnClickListener {
            binding.layoutTodoCertificateToolTip.visibility = View.GONE
        }
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
                    REQUEST_CAMERA_PERMISSION
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
                    REQUEST_CAMERA_PERMISSION_UNDER_Q
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

    companion object {
        const val NEW_IMG_URI = "imgUri"
        const val REQUEST_CAMERA_PERMISSION = 1
        const val REQUEST_CAMERA_PERMISSION_UNDER_Q = 2
    }
}
