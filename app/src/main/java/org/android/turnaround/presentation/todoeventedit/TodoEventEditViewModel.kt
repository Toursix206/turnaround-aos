package org.android.turnaround.presentation.todoeventedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.android.turnaround.data.remote.entity.request.TodoEditRequest
import org.android.turnaround.data.remote.repository.TodoRepository
import org.android.turnaround.domain.entity.Todo
import org.android.turnaround.domain.entity.TodoList
import org.android.turnaround.util.Event
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TodoEventEditViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {
    private val _isClickedDeleteBtnEvent = MutableLiveData<Event<Int>>()
    val isClickedDeleteBtnEvent: LiveData<Event<Int>> = _isClickedDeleteBtnEvent

    private val _isClickedEditBtnEvent = MutableLiveData<Event<Todo>>()
    val isClickedEditBtnEvent: LiveData<Event<Todo>> = _isClickedEditBtnEvent

    private val _isClickedBottomSheetTodoEditBtnEvent = MutableLiveData<Event<Int>>()
    val isClickedBottomSheetTodoEditBtnEvent: LiveData<Event<Int>> = _isClickedBottomSheetTodoEditBtnEvent

    private val _todoList = MutableLiveData<TodoList>()
    val todoList: LiveData<TodoList> = _todoList

    private val _deleteTodo = MutableLiveData<String>()
    val deleteTodo: LiveData<String> = _deleteTodo

    private val _editTodo = MutableLiveData<String>()
    val editTodo: LiveData<String> = _editTodo

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

    fun setIsCheckedEditBtnEvent(todo: Todo) {
        _isClickedEditBtnEvent.value = Event(todo)
    }

    fun setIsCheckedBottomSheetTodoEditBtnEvent(todoId: Int) {
        _isClickedBottomSheetTodoEditBtnEvent.value = Event(todoId)
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

    fun putTodo(todoId: Int, body: TodoEditRequest) = viewModelScope.launch {
        kotlin.runCatching {
            todoRepository.putTodo(todoId, body)
        }.onSuccess {
            _editTodo.value = it.getOrNull()
        }.onFailure {
            Timber.d(it.message)
        }
    }
}
