package org.android.turnaround.presentation.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.android.turnaround.domain.entity.ActivityCategory
import org.android.turnaround.domain.entity.ActivityContent
import org.android.turnaround.domain.entity.PushStatusType
import org.android.turnaround.domain.repository.ActivityRepository
import org.android.turnaround.util.UiEvent
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val activityRepository: ActivityRepository
) : ViewModel() {
    private val _category = MutableStateFlow<ActivityCategory?>(null)
    val category: StateFlow<ActivityCategory?> = _category.asStateFlow()

    private val _reserveTodoUiEvent = MutableSharedFlow<UiEvent>()
    val reserveTodoUiEvent: SharedFlow<UiEvent> = _reserveTodoUiEvent.asSharedFlow()

    var reserveErrorCode: Int = 0
        private set

    fun isErrorDuplicateReserve(): Boolean = reserveErrorCode == ERROR_DUPLICATE_RESERVE

    fun isErrorInvalidReserveDate(): Boolean = reserveErrorCode == ERROR_INVALID_RESERVE_DATE

    fun initCategory(category: ActivityCategory?) {
        _category.value = category
    }

    suspend fun getActivities(): Flow<PagingData<ActivityContent>> =
        activityRepository.getActivities(category = category.value, size = ACTIVITY_LIST_SIZE)
            .cachedIn(viewModelScope)

    fun postReserveTodo(activityId: Int, pushStatus: PushStatusType, startAt: String) {
        viewModelScope.launch {
            _reserveTodoUiEvent.emit(UiEvent.LOADING)
            activityRepository.postReserveTodo(
                activityId = activityId,
                pushStatus = pushStatus,
                startAt = startAt
            ).onSuccess { response ->
                if (response) {
                    _reserveTodoUiEvent.emit(UiEvent.SUCCESS)
                }
            }.onFailure { throwable ->
                Timber.e(throwable.message.toString())
                if (throwable is HttpException) {
                    reserveErrorCode = throwable.code()
                }
                _reserveTodoUiEvent.emit(UiEvent.ERROR)
            }
        }
    }

    companion object {
        const val ACTIVITY_LIST_SIZE = 10
        const val ERROR_DUPLICATE_RESERVE = 409
        const val ERROR_INVALID_RESERVE_DATE = 400
    }
}
