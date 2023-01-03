package org.android.turnaround.presentation.todo_review

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.ActivityTodoReviewBinding
import org.android.turnaround.util.binding.BindingActivity

@AndroidEntryPoint
class TodoReviewActivity : BindingActivity<ActivityTodoReviewBinding>(R.layout.activity_todo_review) {
    private val viewModel by viewModels<TodoReviewViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        viewModel.initDoneReviewId(intent.getIntExtra(REVIEW_ID, -1))
        viewModel.getNotWrittenReview()
        initCloseToolTipBtnClickListener()
    }

    private fun initCloseToolTipBtnClickListener() {
        binding.btnTodoReviewCloseToolTip.setOnClickListener {
            binding.layoutTodoReviewToolTip.visibility = View.GONE
        }
    }

    companion object {
        const val REVIEW_ID = "reviewId"
    }
}
