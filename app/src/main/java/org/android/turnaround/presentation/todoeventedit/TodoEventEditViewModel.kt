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
import retrofit2.HttpException
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

    private val _editTodoFail = MutableLiveData<String>()
    val editTodoFail: LiveData<String> = _editTodoFail

    init {
        getTodoList()
    }

    fun getTodoList() = viewModelScope.launch {
        todoRepository.getTodoList()
            .onSuccess {
                _todoList.value = it
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
        todoRepository.deleteTodo(todoId)
            .onSuccess {
                _deleteTodo.value = it
            }.onFailure {
                Timber.d(it.message)
            }
    }

    fun putTodo(todoId: Int, body: TodoEditRequest) = viewModelScope.launch {
        todoRepository.putTodo(todoId, body)
            .onSuccess {
                _editTodo.value = "\uD83D\uDE42 활동이 변경되었어요!"
            }.onFailure { throwable ->
                Timber.d(throwable.message)
                if (throwable is HttpException) {
                    when (throwable.code()) {
                        ERROR_START_AT -> _editTodoFail.value = "\uD83D\uDE15 정책에 위배되는 예약 시간입니다."
                        ERROR_TOKEN_EXPIRATION -> _editTodoFail.value = "토큰이 만료되었습니다. 다시 로그인 해주세요."
                        ERROR_CANNOT_DELETE -> _editTodoFail.value = "수정/삭제 할 수 없는 일정입니다."
                        ERROR_NO_EXIST -> _editTodoFail.value = "탈퇴했거나 존재하지 않는 유저입니다.\n존재하지 않는 todo 입니다."
                        ERROR_DUPLICATE_TODO -> _editTodoFail.value = "\uD83D\uDE15다른 활동과 겹치는 일정이에요!"
                        ERROR_SERVER -> _editTodoFail.value = "예상치 못한 서버 에러가 발생하였습니다."
                    }
                }
            }
    }

    companion object {
        const val ERROR_START_AT = 400
        const val ERROR_TOKEN_EXPIRATION = 401
        const val ERROR_CANNOT_DELETE = 403
        const val ERROR_NO_EXIST = 404
        const val ERROR_DUPLICATE_TODO = 409
        const val ERROR_SERVER = 500
    }
}
