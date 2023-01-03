package org.android.turnaround.presentation.todo_review

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.android.turnaround.domain.entity.NotWrittenReview

class TodoReviewViewModel : ViewModel() {
    val reviewContent = MutableStateFlow(EMPTY_CONTENT)
    val reviewRating = MutableStateFlow(DEFAULT_RATING)

    private val _notWrittenReview = MutableStateFlow(NotWrittenReview())
    val notWrittenReview: StateFlow<NotWrittenReview> = _notWrittenReview.asStateFlow()

    companion object {
        const val EMPTY_CONTENT = ""
        const val DEFAULT_RATING = 0f
    }
}
