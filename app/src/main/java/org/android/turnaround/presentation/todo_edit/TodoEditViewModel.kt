package org.android.turnaround.presentation.todo_edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.android.turnaround.data.remote.entity.request.TodoEditRequest
import org.android.turnaround.data.remote.entity.response.NoDataResponse
import org.android.turnaround.domain.entity.Todo
import org.android.turnaround.domain.entity.TodoList
import org.android.turnaround.domain.repository.TodoRepository
import org.android.turnaround.util.Event
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TodoEditViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {
    private val _isClickedDeleteBtnEvent = MutableLiveData<Event<Int>>()
    val isClickedDeleteBtnEvent: LiveData<Event<Int>> = _isClickedDeleteBtnEvent

    private val _isClickedEditBtnEvent = MutableLiveData<Event<Todo>>()
    val isClickedEditBtnEvent: LiveData<Event<Todo>> = _isClickedEditBtnEvent

    private val _todoList = MutableLiveData<TodoList>()
    val todoList: LiveData<TodoList> = _todoList

    private val _deleteTodo = MutableLiveData<String>()
    val deleteTodo: LiveData<String> = _deleteTodo

    private val _isEditTodoSuccess = MutableLiveData<Boolean>()
    val isEditTodoSuccess: LiveData<Boolean> = _isEditTodoSuccess

    private val _editTodoErrorCode = MutableLiveData<Int>()
    val editTodoErrorCode: LiveData<Int> = _editTodoErrorCode

    private val _isTodoExist = MutableStateFlow(true)
    val isTodoExist: StateFlow<Boolean> = _isTodoExist.asStateFlow()

    var editTodoErrorMessage: String = ""
        private set

    init {
        getTodoList()
    }

    fun getTodoList() = viewModelScope.launch {
        todoRepository.getTodoList()
            .onSuccess {
                _todoList.value = it
                _isTodoExist.value = it.todayTodosCnt + it.thisWeekTodosCnt + it.nextTodosCnt + it.successTodosCnt > 0
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

    fun deleteTodo(todoId: Int) = viewModelScope.launch {
        todoRepository.deleteTodo(todoId)
            .onSuccess {
                _deleteTodo.value = it
            }.onFailure {
                Timber.d(it.message)
            }
    }

    fun putTodo(todoId: Int, body: TodoEditRequest) =
        viewModelScope.launch(Dispatchers.IO) { // onFailure 의 string()을 위해서 CoroutineContext 설정
            todoRepository.putTodo(todoId, body)
                .onSuccess {
                    _isEditTodoSuccess.postValue(true)
                }.onFailure { throwable ->
                    Timber.d(throwable.message)
                    if (throwable is HttpException) {
                        val errorBodyJson = throwable.response()?.errorBody()?.string()!!.trimIndent()
                        editTodoErrorMessage = Gson().fromJson(errorBodyJson, NoDataResponse::class.java).message
                        _editTodoErrorCode.postValue(throwable.code())
                    }
                }
        }

    companion object {
        const val ERROR_INVALID_TODO_DATE = 400
        const val ERROR_CANNOT_DELETE = 403
        const val ERROR_DUPLICATE_TODO = 409
    }
}
