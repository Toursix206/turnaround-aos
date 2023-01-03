package org.android.turnaround.presentation.todo_review

import android.os.Bundle
import androidx.activity.viewModels
import org.android.turnaround.R
import org.android.turnaround.databinding.ActivityTodoReviewBinding
import org.android.turnaround.util.binding.BindingActivity

class TodoReviewActivity : BindingActivity<ActivityTodoReviewBinding>(R.layout.activity_todo_review) {
    private val viewModel by viewModels<TodoReviewViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
    }

    companion object {
        const val REVIEW_ID = "reviewId"
    }
}
