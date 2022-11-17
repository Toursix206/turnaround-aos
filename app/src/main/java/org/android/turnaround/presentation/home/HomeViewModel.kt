package org.android.turnaround.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.android.turnaround.data.remote.repository.HomeRepository
import org.android.turnaround.data.remote.repository.TodoRepository
import org.android.turnaround.domain.entity.Home
import org.android.turnaround.domain.entity.TodoDetail
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

    private val _todoDetail = MutableLiveData<TodoDetail>()
    val todoDetail: LiveData<TodoDetail> = _todoDetail

    init {
        getHome()
    }

    private fun getHome() = viewModelScope.launch {
        kotlin.runCatching {
            homeRepository.getHome()
        }.onSuccess {
            _home.value = it.getOrNull()

            if ((it.getOrNull()?.todosCnt ?: 0) <= 0) {
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
        kotlin.runCatching {
            todoRepository.getTodoDetail(todoId)
        }.onSuccess {
            _todoDetail.value = it.getOrNull()
        }.onFailure {
            Timber.d(it.message)
        }
    }
}
