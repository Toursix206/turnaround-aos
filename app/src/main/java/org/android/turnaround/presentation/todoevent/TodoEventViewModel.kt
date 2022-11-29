package org.android.turnaround.presentation.todoevent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.android.turnaround.domain.repository.TodoRepository
import org.android.turnaround.domain.entity.TodoDetail
import org.android.turnaround.domain.entity.TodoList
import org.android.turnaround.domain.entity.TodoReward
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TodoEventViewModel @Inject constructor(
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

    init {
        getTodoList()
    }

    fun getTodoList() = viewModelScope.launch {
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
                _alarmOff.value = "모든 알람을 껐습니다."
            }.onFailure { throwable ->
                Timber.d(throwable.message)
                if (throwable is HttpException) {
                    when (throwable.code()) {
                        DUPLICATE_ALARM_OFF -> _alarmOff.value = "이미 모든 활동에 대한 알림이 꺼져있습니다."
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

    companion object {
        const val DUPLICATE_ALARM_OFF = 409
    }
}
