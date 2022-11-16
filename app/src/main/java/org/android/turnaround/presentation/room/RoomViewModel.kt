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
import org.android.turnaround.domain.entity.RoomInfo
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : ViewModel() {
    private val _clickedWindow = MutableSharedFlow<Boolean>()
    val clickedWindow: SharedFlow<Boolean> = _clickedWindow.asSharedFlow()

    private val _clickedTable = MutableSharedFlow<Boolean>()
    val clickedTable: SharedFlow<Boolean> = _clickedTable.asSharedFlow()

    private val _clickedBed = MutableSharedFlow<Boolean>()
    val clickedBed: SharedFlow<Boolean> = _clickedBed.asSharedFlow()

    private val _showWindowBrush = MutableStateFlow(false)
    val showWindowBrush: StateFlow<Boolean> = _showWindowBrush.asStateFlow()

    private val _showTableBrush = MutableStateFlow(false)
    val showTableBrush: StateFlow<Boolean> = _showTableBrush.asStateFlow()

    private val _showBedBrush = MutableStateFlow(false)
    val showBedBrush: StateFlow<Boolean> = _showBedBrush.asStateFlow()

    var windowStartLevel = CleanLevel.VERY_DIRTY
        private set
    var bedStartLevel = CleanLevel.VERY_DIRTY
        private set
    var tableStartLevel = CleanLevel.VERY_DIRTY
        private set

    private val _windowLevel = MutableStateFlow(windowStartLevel)
    val windowLevel: StateFlow<CleanLevel> = _windowLevel.asStateFlow()

    private val _bedLevel = MutableStateFlow(bedStartLevel)
    val bedLevel: StateFlow<CleanLevel> = _bedLevel.asStateFlow()

    private val _tableLevel = MutableStateFlow(tableStartLevel)
    val tableLevel: StateFlow<CleanLevel> = _tableLevel.asStateFlow()

    private val _roomInfo = MutableStateFlow(RoomInfo())
    val roomInfo: StateFlow<RoomInfo> = _roomInfo.asStateFlow()

    fun initAllRoomFurniture() {
        _showWindowBrush.value = false
        _showBedBrush.value = false
        _showTableBrush.value = false
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

    fun initShowTableBrush() {
        val newShow = !requireNotNull(showTableBrush.value)
        initAllRoomFurniture()
        _showTableBrush.value = newShow
        viewModelScope.launch { _clickedTable.emit(true) }
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

    fun initTableLevel() {
        _tableLevel.value = getNewCleanLevel(tableLevel.value)
    }
}
