package org.android.turnaround.presentation.todoevent

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
import org.android.turnaround.data.remote.repository.AuthRepository
import org.android.turnaround.data.remote.repository.TodoRepository
import org.android.turnaround.domain.entity.ProfileType
import org.android.turnaround.domain.entity.Todo
import org.android.turnaround.domain.entity.TodoList
import retrofit2.HttpException
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
                    Timber.d("mmm$it")
                }
                .onFailure { throwable ->
                    Timber.d("mmm" + throwable.message)
                }
        }
    }
}
