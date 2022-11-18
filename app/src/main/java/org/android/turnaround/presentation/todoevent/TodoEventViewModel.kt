package org.android.turnaround.presentation.todoevent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.android.turnaround.data.remote.repository.TodoRepository
import org.android.turnaround.domain.entity.TodoDetail
import org.android.turnaround.domain.entity.TodoList
import org.android.turnaround.util.Event
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TodoEventViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {
    private val _isTodoExist = MutableStateFlow(false)
    val isTodoExist: StateFlow<Boolean> = _isTodoExist.asStateFlow()

    private val _isClickedBlackItemEvent = MutableLiveData<Event<Int>>()
    val isClickedBlackItemEvent: LiveData<Event<Int>> = _isClickedBlackItemEvent

    private val _todoList = MutableLiveData<TodoList>()
    val todoList: LiveData<TodoList> = _todoList

    private val _todoDetail = MutableLiveData<TodoDetail>()
    val todoDetail: LiveData<TodoDetail> = _todoDetail

    private val _alarmOff = MutableLiveData<String>()
    val alarmOff: LiveData<String> = _alarmOff

    init {
        getTodoList()
    }

    private fun getTodoList() = viewModelScope.launch {
        todoRepository.getTodoList()
            .onSuccess {
                _todoList.value = it

                if (it.todayTodosCnt + it.thisWeekTodosCnt + it.nextTodosCnt + it.nextTodosCnt <= 0) {
                    _isTodoExist.value = true
                }
            }
            .onFailure { throwable ->
                Timber.d(throwable.message)
            }
    }

    fun setBlackTodoId(todoId: Int) {
        _isClickedBlackItemEvent.value = Event(todoId)
    }

    fun getTodoDetail(todoId: Int) = viewModelScope.launch {
        todoRepository.getTodoDetail(todoId)
            .onSuccess {
                _todoDetail.value = it
            }.onFailure {
                Timber.d(it.message)
            }
    }

    fun putNotificationOff() = viewModelScope.launch {
        todoRepository.putNotificationOff()
            .onSuccess {
                _alarmOff.value = it
            }.onFailure { throwable ->
                Timber.d(throwable.message)
                if (throwable is HttpException) {
                    when (throwable.code()) {
                        DUPLICATE_ALARM_OFF -> _alarmOff.value = "이미 모든 활동에 대한 알림이 꺼져있습니다."
                    }
                }
            }
    }

    companion object {
        const val DUPLICATE_ALARM_OFF = 409
    }
}
