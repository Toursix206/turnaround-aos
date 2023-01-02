package org.android.turnaround.presentation.todo_guide

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.android.turnaround.domain.entity.Guide
import org.android.turnaround.domain.repository.ActivityRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TodoGuideViewModel @Inject constructor(
    private val activityRepository: ActivityRepository
) : ViewModel() {
    var todoId = -1
        private set

    private val _isDoingTodo = MutableStateFlow(false)
    val isDoingTodo: StateFlow<Boolean> = _isDoingTodo.asStateFlow()

    private val _currentStep = MutableStateFlow(0)
    val currentStep: StateFlow<Int> = _currentStep.asStateFlow()

    private val _activityName = MutableStateFlow("")
    val activityName: StateFlow<String> = _activityName.asStateFlow()

    private val _guides = MutableStateFlow<List<Guide>>(emptyList())
    val guides: StateFlow<List<Guide>> = _guides.asStateFlow()

    fun initTodoId(todoId: Int) {
        this.todoId = todoId
    }

    fun initIsDoingTodo(isDoing: Boolean) {
        _isDoingTodo.value = isDoing
    }

    fun initNextStep() {
        if (currentStep.value < guides.value.size) {
            _currentStep.value = currentStep.value + 1
        } else {
            _isDoingTodo.value = false
        }
    }

    fun getTodoGuide() {
        viewModelScope.launch {
            activityRepository.getTodoGuide(todoId)
                .onSuccess { response ->
                    _activityName.value = response.name
                    _guides.value = response.guides
                    _currentStep.value = 1
                }
                .onFailure { Timber.d(it.message.toString()) }
        }
    }
}
