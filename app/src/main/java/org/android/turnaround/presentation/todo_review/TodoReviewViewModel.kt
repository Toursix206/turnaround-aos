package org.android.turnaround.presentation.todo_review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.android.turnaround.domain.entity.NotWrittenReview
import org.android.turnaround.domain.repository.TodoRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TodoReviewViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {
    private var doneReviewId: Int = -1
    val reviewContent = MutableStateFlow(EMPTY_CONTENT)
    val reviewRating = MutableStateFlow(DEFAULT_RATING)

    private val _review = MutableStateFlow(NotWrittenReview())
    val review: StateFlow<NotWrittenReview> = _review.asStateFlow()

    fun initDoneReviewId(doneReviewId: Int) {
        this.doneReviewId = doneReviewId
    }

    fun getNotWrittenReview() {
        viewModelScope.launch {
            todoRepository.getNotWrittenReview(doneReviewId)
                .onSuccess { response ->
                    _review.value = response
                    reviewRating.value = response.rating.score.toFloat()
                }
                .onFailure { Timber.d(it.message.toString()) }
        }
    }

    companion object {
        const val EMPTY_CONTENT = ""
        const val DEFAULT_RATING = 0f
    }
}
