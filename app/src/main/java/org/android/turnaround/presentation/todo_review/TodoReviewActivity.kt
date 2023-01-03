package org.android.turnaround.presentation.todo_review

import android.os.Bundle
import org.android.turnaround.R
import org.android.turnaround.databinding.ActivityTodoReviewBinding
import org.android.turnaround.util.binding.BindingActivity

class TodoReviewActivity : BindingActivity<ActivityTodoReviewBinding>(R.layout.activity_todo_review) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        const val REVIEW_ID = "reviewId"
    }
}
