package org.android.turnaround.presentation.my_todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import org.android.turnaround.domain.entity.TodoDetail
import org.android.turnaround.domain.entity.TodoList
import org.android.turnaround.domain.entity.TodoReward
import org.android.turnaround.domain.repository.TodoRepository
import org.android.turnaround.util.UiEvent
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyTodoViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {
    private val _isTodoExist = MutableStateFlow(false)
    val isTodoExist: StateFlow<Boolean> = _isTodoExist.asStateFlow()

    private val _todoList = MutableLiveData<TodoList>()
    val todoList: LiveData<TodoList> = _todoList

    private val _todoDetail = MutableLiveData<TodoDetail>()
    val todoDetail: LiveData<TodoDetail> = _todoDetail

    private val _alarmOff = MutableLiveData<String>()
    val alarmOff: LiveData<String> = _alarmOff

    private val _todoReward = MutableLiveData<TodoReward>()
    val todoReward: LiveData<TodoReward> = _todoReward

    private val _todoStartAbleEvent = MutableSharedFlow<UiEvent>()
    val todoStartAbleEvent: SharedFlow<UiEvent> = _todoStartAbleEvent.asSharedFlow()

    init {
        getTodoList()
    }

    fun getTodoList() = viewModelScope.launch {
        todoRepository.getTodoList()
            .onSuccess {
                _todoList.value = it

                if (it.todayTodosCnt + it.thisWeekTodosCnt + it.nextTodosCnt + it.successTodosCnt <= 0) {
                    _isTodoExist.value = true
                }
            }
            .onFailure { throwable ->
                Timber.d(throwable.message)
            }
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
                _alarmOff.value = "\uD83D\uDE42 예약된 모든 활동의 알람을 받지 않아요 "
            }.onFailure { throwable ->
                Timber.d(throwable.message)
                if (throwable is HttpException) {
                    when (throwable.code()) {
                        ERROR_DUPLICATE_ALARM_OFF -> _alarmOff.value = "\uD83D\uDE42 예약된 모든 활동의 알람을 받지 않아요 "
                    }
                }
            }
    }

    fun putTodoReward(todoId: Int) = viewModelScope.launch {
        todoRepository.putTodoReward(todoId)
            .onSuccess {
                _todoReward.value = it
            }.onFailure { throwable ->
                Timber.d(throwable.message)
            }
    }

    fun getTodoStartAble(todoId: Int) {
        viewModelScope.launch {
            _todoStartAbleEvent.emit(UiEvent.LOADING)
            todoRepository.getTodoStartAble(todoId)
                .onSuccess {
                    _todoStartAbleEvent.emit(UiEvent.SUCCESS)
                }
                .onFailure { throwable ->
                    if (throwable is HttpException) {
                        if (throwable.code() == ERROR_DUPLICATE_TODO) {
                            _todoStartAbleEvent.emit(UiEvent.ERROR)
                        }
                    }
                }
        }
    }

    companion object {
        const val ERROR_DUPLICATE_ALARM_OFF = 409
        const val ERROR_DUPLICATE_TODO = 409
    }
}
