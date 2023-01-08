package org.android.turnaround.presentation.todo_review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.android.turnaround.domain.entity.Review
import org.android.turnaround.domain.repository.TodoRepository
import org.android.turnaround.util.UiEvent
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TodoReviewViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {
    private var doneReviewId: Int = -1
    val reviewContent = MutableStateFlow(EMPTY_CONTENT)
    val reviewRating = MutableStateFlow(DEFAULT_RATING)

    private val _review = MutableStateFlow(Review())
    val review: StateFlow<Review> = _review.asStateFlow()

    private val _postReviewEvent = MutableSharedFlow<UiEvent>()
    val postReviewEvent: SharedFlow<UiEvent> = _postReviewEvent.asSharedFlow()

    fun initDoneReviewId(doneReviewId: Int) {
        this.doneReviewId = doneReviewId
    }

    fun getNotWrittenReview() {
        viewModelScope.launch {
            todoRepository.getNotWrittenReview(doneReviewId)
                .onSuccess { response ->
                    _review.value = response
                    reviewContent.value = response.content
                    reviewRating.value = response.score.toFloat()
                }
                .onFailure { Timber.d(it.message.toString()) }
        }
    }

    fun postReview() {
        viewModelScope.launch {
            _postReviewEvent.emit(UiEvent.LOADING)
            todoRepository.postReview(
                doneReviewId = doneReviewId,
                content = reviewContent.value,
                rating = reviewRating.value.toInt()
            ).onSuccess {
                _postReviewEvent.emit(UiEvent.SUCCESS)
            }.onFailure {
                Timber.d(it.message.toString())
                _postReviewEvent.emit(UiEvent.ERROR)
            }
        }
    }

    companion object {
        const val EMPTY_CONTENT = ""
        const val DEFAULT_RATING = 0f
    }
}
