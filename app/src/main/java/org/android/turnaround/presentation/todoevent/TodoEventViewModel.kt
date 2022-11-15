package org.android.turnaround.presentation.todoevent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.android.turnaround.data.remote.repository.TodoRepository
import org.android.turnaround.domain.entity.TodoList
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TodoEventViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {

    private val _todoList = MutableSharedFlow<TodoList>()
    val todoList: SharedFlow<TodoList> = _todoList.asSharedFlow()

    init {
        getTodoList()
    }

    private fun getTodoList() {
        viewModelScope.launch {
            todoRepository.getTodoList()
                .onSuccess {
                    _todoList.emit(it)
                }
                .onFailure { throwable ->
                    Timber.d(throwable.message)
                }
        }
    }
}
