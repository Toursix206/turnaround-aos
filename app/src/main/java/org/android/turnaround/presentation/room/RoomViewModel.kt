package org.android.turnaround.presentation.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.android.turnaround.domain.entity.CleanScore

class RoomViewModel : ViewModel() {
    private val _clickedWindow = MutableSharedFlow<Boolean>()
    val clickedWindow: SharedFlow<Boolean> = _clickedWindow.asSharedFlow()

    private val _clickedDesk = MutableSharedFlow<Boolean>()
    val clickedDesk: SharedFlow<Boolean> = _clickedDesk.asSharedFlow()

    private val _clickedBed = MutableSharedFlow<Boolean>()
    val clickedBed: SharedFlow<Boolean> = _clickedBed.asSharedFlow()

    private val _showWindowBrush = MutableStateFlow(false)
    val showWindowBrush: StateFlow<Boolean> = _showWindowBrush.asStateFlow()

    private val _showDeskBrush = MutableStateFlow(false)
    val showDeskBrush: StateFlow<Boolean> = _showDeskBrush.asStateFlow()

    private val _showBedBrush = MutableStateFlow(false)
    val showBedBrush: StateFlow<Boolean> = _showBedBrush.asStateFlow()

    var windowStartScore = CleanScore.VERY_DIRTY
        private set
    var bedStartScore = CleanScore.VERY_DIRTY
        private set
    var deskStartScore = CleanScore.VERY_DIRTY
        private set

    private val _windowScore = MutableStateFlow(windowStartScore)
    val windowScore: StateFlow<CleanScore> = _windowScore.asStateFlow()

    private val _bedScore = MutableStateFlow(bedStartScore)
    val bedScore: StateFlow<CleanScore> = _bedScore.asStateFlow()

    private val _deskScore = MutableStateFlow(deskStartScore)
    val deskScore: StateFlow<CleanScore> = _deskScore.asStateFlow()

    fun initAllRoomFurniture() {
        _showWindowBrush.value = false
        _showBedBrush.value = false
        _showDeskBrush.value = false
    }

    fun initShowWindowBrush() {
        val newShow = !requireNotNull(showWindowBrush.value)
        initAllRoomFurniture()
        _showWindowBrush.value = newShow
        viewModelScope.launch { _clickedWindow.emit(true) }
    }

    fun initShowBedBrush() {
        val newShow = !requireNotNull(showBedBrush.value)
        initAllRoomFurniture()
        _showBedBrush.value = newShow
        viewModelScope.launch { _clickedBed.emit(true) }
    }

    fun initShowDeskBrush() {
        val newShow = !requireNotNull(showDeskBrush.value)
        initAllRoomFurniture()
        _showDeskBrush.value = newShow
        viewModelScope.launch { _clickedDesk.emit(true) }
    }

    private fun getNewCleanScore(oldScore: CleanScore): CleanScore =
        when (oldScore) {
            CleanScore.CLEAN -> CleanScore.CLEAN
            CleanScore.VERY_DIRTY -> CleanScore.DIRTY
            CleanScore.DIRTY -> CleanScore.CLEAN
        }

    fun initWindowScore() {
        _windowScore.value = getNewCleanScore(windowScore.value)
    }

    fun initBedScore() {
        _bedScore.value = getNewCleanScore(bedScore.value)
    }

    fun initDeskScore() {
        _deskScore.value = getNewCleanScore(deskScore.value)
    }
}
