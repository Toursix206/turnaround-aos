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
        kotlin.runCatching {
            todoRepository.getTodoDetail(todoId)
        }.onSuccess {
            _todoDetail.value = it.getOrNull()
        }.onFailure {
            Timber.d(it.message)
        }
    }
}
