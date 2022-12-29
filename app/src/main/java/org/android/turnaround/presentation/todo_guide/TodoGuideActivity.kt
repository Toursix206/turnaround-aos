package org.android.turnaround.presentation.todo_guide

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.ActivityTodoGuideBinding
import org.android.turnaround.presentation.todo_certificate.TodoCertificateActivity
import org.android.turnaround.presentation.todo_guide.adapter.TodoGuideAdapter
import org.android.turnaround.util.binding.BindingActivity
import org.android.turnaround.util.dialog.DialogBtnClickListener
import org.android.turnaround.util.dialog.WarningDialogFragment
import org.android.turnaround.util.dialog.WarningType
import org.android.turnaround.util.extension.repeatOnStarted
import org.android.turnaround.util.getImgUri
import org.android.turnaround.util.getPathFromUri
import timber.log.Timber
import java.io.File

@AndroidEntryPoint
class TodoGuideActivity : BindingActivity<ActivityTodoGuideBinding>(R.layout.activity_todo_guide) {
    private val viewModel by viewModels<TodoGuideViewModel>()
    private val todoGuideAdapter = TodoGuideAdapter()
    private var imgUri: Uri? = null
    private val fromCameraActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        imgUri?.let { uri ->
            Thread.sleep(700)
            if (File(getPathFromUri(this, uri)).exists()) {
                startActivity(
                    Intent(this, TodoCertificateActivity::class.java).apply {
                        putExtra(IMG_URI, uri.toString())
                    }
                )
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        viewModel.getTodoGuide(intent.getIntExtra(TODO_GUIDE_TODO_ID, -1))
        initGuideImgViewPager()
        initCloseBtnClickListener()
        initCloseToolTipBtnClickListener()
        initDoneBtnClickListener()
        initGuidesCollector()
        initCurrentStepCollector()
    }

    private fun initGuideImgViewPager() {
        with(binding.vpTodoGuide) {
            adapter = todoGuideAdapter
            isUserInputEnabled = false
        }
    }

    private fun initCloseBtnClickListener() {
        binding.btnTodoGuideClose.setOnClickListener {
            if (viewModel.isDoingTodo.value || viewModel.currentStep.value == viewModel.guides.value.size) {
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
            } else {
                finish()
            }
        }
    }

    private fun initCloseToolTipBtnClickListener() {
        binding.btnTodoGuideCloseToolTip.setOnClickListener {
            binding.layoutTodoGuideToolTip.visibility = View.GONE
        }
    }

    private fun initDoneBtnClickListener() {
        binding.btnTodoGuideDone.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (checkCameraPermission()) {
                    takePicture()
                } else {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.CAMERA),
                        REQUEST_CAMERA_PERMISSION
                    )
                }
            } else {
                if (checkCameraPermissionUnderQ()) {
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
    }

    private fun checkCameraPermission() =
        ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

    private fun checkCameraPermissionUnderQ() =
        checkCameraPermission() && ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

    private fun takePicture() {
        try {
            imgUri = getImgUri(contentResolver)
                ?: throw NullPointerException()
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
                it.putExtra(MediaStore.EXTRA_OUTPUT, imgUri)
                fromCameraActivityLauncher.launch(it)
            }
        } catch (e: NullPointerException) {
            Timber.tag("ProfileBottomSheet").e("ImgUri Null 에러")
        }
    }

    private fun initGuidesCollector() {
        repeatOnStarted {
            viewModel.guides.collect { guides ->
                todoGuideAdapter.submitList(guides)
            }
        }
    }

    private fun initCurrentStepCollector() {
        repeatOnStarted {
            viewModel.currentStep.collect { step ->
                binding.vpTodoGuide.currentItem = step - 1
            }
        }
    }

    companion object {
        const val TODO_GUIDE_TODO_ID = "todoGuideTodoId"
        const val IMG_URI = "imageUri"
        const val REQUEST_CAMERA_PERMISSION = 2
        const val REQUEST_CAMERA_PERMISSION_UNDER_Q = 3
    }
}
