package org.android.turnaround.presentation.room

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
import org.android.turnaround.data.remote.repository.RoomRepository
import org.android.turnaround.domain.entity.CleanLevel
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : ViewModel() {
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

    var windowStartLevel = CleanLevel.VERY_DIRTY
        private set
    var bedStartLevel = CleanLevel.VERY_DIRTY
        private set
    var deskStartLevel = CleanLevel.VERY_DIRTY
        private set

    private val _windowLevel = MutableStateFlow(windowStartLevel)
    val windowLevel: StateFlow<CleanLevel> = _windowLevel.asStateFlow()

    private val _bedLevel = MutableStateFlow(bedStartLevel)
    val bedLevel: StateFlow<CleanLevel> = _bedLevel.asStateFlow()

    private val _deskLevel = MutableStateFlow(deskStartLevel)
    val deskLevel: StateFlow<CleanLevel> = _deskLevel.asStateFlow()

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

    private fun getNewCleanLevel(oldLevel: CleanLevel): CleanLevel =
        when (oldLevel) {
            CleanLevel.CLEAN -> CleanLevel.CLEAN
            CleanLevel.VERY_DIRTY -> CleanLevel.DIRTY
            CleanLevel.DIRTY -> CleanLevel.CLEAN
        }

    fun initWindowLevel() {
        _windowLevel.value = getNewCleanLevel(windowLevel.value)
    }

    fun initBedLevel() {
        _bedLevel.value = getNewCleanLevel(bedLevel.value)
    }

    fun initDeskLevel() {
        _deskLevel.value = getNewCleanLevel(deskLevel.value)
    }
}
