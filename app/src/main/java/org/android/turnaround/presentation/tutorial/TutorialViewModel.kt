package org.android.turnaround.presentation.tutorial

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TutorialViewModel : ViewModel() {
    private val _currentTutorial = MutableStateFlow(0)
    val currentTutorial: StateFlow<Int> = _currentTutorial.asStateFlow()

    fun initCurrentTutorial(current: Int) {
        _currentTutorial.value = current
    }

    fun nextCurrentTutorial() {
        if (currentTutorial.value < 2) _currentTutorial.value++
    }
}
