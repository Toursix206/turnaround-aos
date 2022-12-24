package org.android.turnaround.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.android.turnaround.domain.entity.Home
import org.android.turnaround.domain.entity.TodoDetail
import org.android.turnaround.domain.repository.HomeRepository
import org.android.turnaround.domain.repository.TodoRepository
import org.android.turnaround.presentation.my_todo.MyTodoViewModel
import org.android.turnaround.util.Event
import org.android.turnaround.util.UiEvent
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val todoRepository: TodoRepository
) : ViewModel() {
    private val _isTodayTodoExist = MutableLiveData<Boolean>()
    val isTodayTodoExist: LiveData<Boolean> = _isTodayTodoExist

    private val _isClickedBlackItemEvent = MutableLiveData<Event<Int>>()
    val isClickedBlackItemEvent: LiveData<Event<Int>> = _isClickedBlackItemEvent

    private val _home = MutableLiveData<Home>()
    val home: LiveData<Home> = _home

    private val _todoDetail = MutableLiveData<Event<TodoDetail>>()
    val todoDetail: LiveData<Event<TodoDetail>> = _todoDetail

    private val _todoStartAbleEvent = MutableSharedFlow<UiEvent>()
    val todoStartAbleEvent: SharedFlow<UiEvent> = _todoStartAbleEvent.asSharedFlow()

    fun getHomeInfo() = viewModelScope.launch {
        homeRepository.getHome()
            .onSuccess {
                _home.value = it

                _isTodayTodoExist.value = (it.todosCnt) <= 0
            }.onFailure {
                Timber.d(it.message)
            }
    }

    fun setBlackTodoId(todoId: Int) {
        _isClickedBlackItemEvent.value = Event(todoId)
    }

    fun getTodoDetail(todoId: Int) = viewModelScope.launch {
        todoRepository.getTodoDetail(todoId)
            .onSuccess {
                _todoDetail.value = Event(it)
            }.onFailure {
                Timber.d(it.message)
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
                        if (throwable.code() == MyTodoViewModel.ERROR_DUPLICATE_TODO) {
                            _todoStartAbleEvent.emit(UiEvent.ERROR)
                        }
                    }
                }
        }
    }
}
