package org.android.turnaround.presentation.todo_review

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.ActivityTodoReviewBinding
import org.android.turnaround.util.UiEvent
import org.android.turnaround.util.binding.BindingActivity
import org.android.turnaround.util.dialog.DialogBtnClickListener
import org.android.turnaround.util.dialog.WarningDialogFragment
import org.android.turnaround.util.dialog.WarningType
import org.android.turnaround.util.extension.repeatOnStarted

@AndroidEntryPoint
class TodoReviewActivity : BindingActivity<ActivityTodoReviewBinding>(R.layout.activity_todo_review) {
    private val viewModel by viewModels<TodoReviewViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        viewModel.initDoneReviewId(intent.getIntExtra(REVIEW_ID, -1))
        viewModel.getNotWrittenReview()
        initCloseToolTipBtnClickListener()
        initSkipReviewBtnClickListener()
        initPostReviewEventCollector()
    }

    private fun initCloseToolTipBtnClickListener() {
        binding.btnTodoReviewCloseToolTip.setOnClickListener {
            binding.layoutTodoReviewToolTip.visibility = View.GONE
        }
    }

    private fun initSkipReviewBtnClickListener() {
        binding.tvTodoReviewSkip.setOnClickListener { skipReview() }
        onBackPressedDispatcher.addCallback { skipReview() }
    }

    private fun skipReview() {
        WarningDialogFragment().apply {
            arguments = Bundle().apply {
                putSerializable(
                    WarningDialogFragment.WARNING_TYPE,
                    WarningType.WARNING_SKIP_REVIEW
                )
                putParcelable(
                    WarningDialogFragment.CONFIRM_ACTION,
                    DialogBtnClickListener(clickAction = { finish() })
                )
            }
        }.show(supportFragmentManager, WarningDialogFragment.DIALOG_WARNING)
    }

    private fun initPostReviewEventCollector() {
        repeatOnStarted {
            viewModel.postReviewEvent.collect { uiEvent ->
                when (uiEvent) {
                    UiEvent.SUCCESS -> finish()
                    UiEvent.ERROR, UiEvent.LOADING -> {}
                }
            }
        }
    }

    companion object {
        const val REVIEW_ID = "reviewId"
    }
}
