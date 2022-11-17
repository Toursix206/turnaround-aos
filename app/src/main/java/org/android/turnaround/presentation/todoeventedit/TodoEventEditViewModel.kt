package org.android.turnaround.presentation.todoeventedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.android.turnaround.data.remote.repository.TodoRepository
import org.android.turnaround.domain.entity.TodoList
import org.android.turnaround.util.Event
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TodoEventEditViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {
    private val _isClickedBlackItemEvent = MutableLiveData<Event<Int>>()
    val isClickedBlackItemEvent: LiveData<Event<Int>> = _isClickedBlackItemEvent

    private val _isClickedDeleteBtnEvent = MutableLiveData<Event<Int>>()
    val isClickedDeleteBtnEvent: LiveData<Event<Int>> = _isClickedDeleteBtnEvent

    private val _todoList = MutableLiveData<TodoList>()
    val todoList: LiveData<TodoList> = _todoList

    private val _deleteTodo = MutableLiveData<String>()
    val deleteTodo: LiveData<String> = _deleteTodo

    init {
        getTodoList()
    }

    fun getTodoList() = viewModelScope.launch {
        kotlin.runCatching {
            todoRepository.getTodoList()
        }.onSuccess {
            _todoList.value = it.getOrNull()
        }.onFailure {
            Timber.d(it.message)
        }
    }

    fun setIsCheckedDeleteBtnEvent(todoId: Int) {
        _isClickedDeleteBtnEvent.value = Event(todoId)
    }

    fun deleteTodo(todoId: Int) = viewModelScope.launch {
        kotlin.runCatching {
            todoRepository.deleteTodo(todoId)
        }.onSuccess {
            _deleteTodo.value = it.getOrNull()
        }.onFailure {
            Timber.d(it.message)
        }
    }
}
