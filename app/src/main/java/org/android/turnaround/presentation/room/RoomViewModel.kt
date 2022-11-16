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
import org.android.turnaround.domain.entity.Furniture
import org.android.turnaround.domain.entity.FurnitureType
import org.android.turnaround.domain.entity.RoomInfo
import timber.log.Timber
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

    private val _windowLevel = MutableStateFlow(CleanLevel.CLEAN)
    val windowLevel: StateFlow<CleanLevel> = _windowLevel.asStateFlow()

    private val _bedLevel = MutableStateFlow(CleanLevel.CLEAN)
    val bedLevel: StateFlow<CleanLevel> = _bedLevel.asStateFlow()

    private val _tableLevel = MutableStateFlow(CleanLevel.CLEAN)
    val tableLevel: StateFlow<CleanLevel> = _tableLevel.asStateFlow()

    private val _windowCleanable = MutableStateFlow(false)
    val windowCleanable: StateFlow<Boolean> = _windowCleanable.asStateFlow()

    private val _bedCleanable = MutableStateFlow(false)
    val bedCleanable: StateFlow<Boolean> = _bedCleanable.asStateFlow()

    private val _tableCleanable = MutableStateFlow(false)
    val tableCleanable: StateFlow<Boolean> = _tableCleanable.asStateFlow()

    private val _isSuccessGetRoomInfo = MutableStateFlow(false)
    val isSuccessGetRoomInfo: StateFlow<Boolean> = _isSuccessGetRoomInfo.asStateFlow()

    private val _roomInfo = MutableStateFlow(RoomInfo())
    val roomInfo: StateFlow<RoomInfo> = _roomInfo.asStateFlow()

    fun resetIsSuccessGetRoomInfo() {
        _isSuccessGetRoomInfo.value = false
    }

    fun initAllRoomFurniture() {
        _showWindowBrush.value = false
        _showBedBrush.value = false
        _showTableBrush.value = false
    }

    fun initShowWindowBrush() {
        val newShow = !requireNotNull(showWindowBrush.value)
        initAllRoomFurniture()
        if (windowCleanable.value) _showWindowBrush.value = newShow
        viewModelScope.launch { _clickedWindow.emit(true) }
    }

    fun initShowBedBrush() {
        val newShow = !requireNotNull(showBedBrush.value)
        initAllRoomFurniture()
        if (bedCleanable.value) _showBedBrush.value = newShow
        viewModelScope.launch { _clickedBed.emit(true) }
    }

    fun initShowTableBrush() {
        val newShow = !requireNotNull(showTableBrush.value)
        initAllRoomFurniture()
        if (tableCleanable.value) _showTableBrush.value = newShow
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

    fun getRoomInfo() {
        viewModelScope.launch {
            roomRepository.getRoomInfo()
                .onSuccess { response ->
                    Timber.d(response.toString())
                    initFurnitureInfo(response.furnitureList)
                    _roomInfo.value = response
                    _isSuccessGetRoomInfo.value = true
                }
                .onFailure { Timber.d(it.message.toString()) }
        }
    }

    private fun initFurnitureInfo(furnitureList: List<Furniture>) {
        for (furniture in furnitureList) {
            when (furniture.furnitureName) {
                FurnitureType.BASIC_WALL -> continue
                FurnitureType.BASIC_WINDOW -> {
                    _windowLevel.value = furniture.furnitureCleanLevel
                    _windowCleanable.value = furniture.isCleanable
                }
                FurnitureType.BASIC_BED -> {
                    _bedLevel.value = furniture.furnitureCleanLevel
                    _bedCleanable.value = furniture.isCleanable
                }
                FurnitureType.BASIC_TABLE -> {
                    _tableLevel.value = furniture.furnitureCleanLevel
                    _tableCleanable.value = furniture.isCleanable
                }
            }
        }
    }
}
