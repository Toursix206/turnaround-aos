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
    private val _currentStep = MutableStateFlow(1)
    val currentStep: StateFlow<Int> = _currentStep.asStateFlow()

    private val _guides = MutableStateFlow<List<Guide>>(emptyList())
    val guides: StateFlow<List<Guide>> = _guides.asStateFlow()

    fun initNextStep() {
        _currentStep.value = currentStep.value + 1
    }

    fun getTodoGuide(activityId: Int) {
        viewModelScope.launch {
            activityRepository.getTodoGuide(activityId)
                .onSuccess { response ->
                    _guides.value = response.guides
                }
                .onFailure { Timber.d(it.message.toString()) }
        }
    }
}
