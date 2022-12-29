package org.android.turnaround.presentation.todo_certificate

import android.os.Bundle
import org.android.turnaround.R
import org.android.turnaround.databinding.ActivityTodoCertificateBinding
import org.android.turnaround.presentation.todo_guide.TodoGuideActivity
import org.android.turnaround.util.binding.BindingActivity

class TodoCertificateActivity : BindingActivity<ActivityTodoCertificateBinding>(R.layout.activity_todo_certificate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.imgUri = intent.getStringExtra(TodoGuideActivity.IMG_URI)
    }
}
