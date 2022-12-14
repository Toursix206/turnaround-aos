package org.android.turnaround.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.android.turnaround.domain.entity.Home
import org.android.turnaround.domain.entity.TodoDetail
import org.android.turnaround.domain.repository.HomeRepository
import org.android.turnaround.domain.repository.TodoRepository
import org.android.turnaround.util.Event
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val todoRepository: TodoRepository
) : ViewModel() {
    private val _isTodayTodoExist = MutableStateFlow(false)
    val isTodayTodoExist: StateFlow<Boolean> = _isTodayTodoExist.asStateFlow()

    private val _isClickedBlackItemEvent = MutableLiveData<Event<Int>>()
    val isClickedBlackItemEvent: LiveData<Event<Int>> = _isClickedBlackItemEvent

    private val _home = MutableLiveData<Home>()
    val home: LiveData<Home> = _home

    private val _todoDetail = MutableLiveData<Event<TodoDetail>>()
    val todoDetail: LiveData<Event<TodoDetail>> = _todoDetail

    init {
        getHomeInfo()
    }

    private fun getHomeInfo() = viewModelScope.launch {
        homeRepository.getHome()
            .onSuccess {
                _home.value = it

                if ((it.todosCnt) <= 0) {
                    _isTodayTodoExist.value = true
                }
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
}
