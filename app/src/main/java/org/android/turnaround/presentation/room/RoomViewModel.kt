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
import org.android.turnaround.domain.entity.Furniture
import org.android.turnaround.domain.entity.FurnitureType
import org.android.turnaround.domain.entity.RoomInfo
import org.android.turnaround.domain.repository.RoomRepository
import org.android.turnaround.util.UiEvent
import retrofit2.HttpException
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

    private val _window = MutableStateFlow(Furniture())
    val window: StateFlow<Furniture> = _window.asStateFlow()

    private val _bed = MutableStateFlow(Furniture())
    val bed: StateFlow<Furniture> = _bed.asStateFlow()

    private val _table = MutableStateFlow(Furniture())
    val table: StateFlow<Furniture> = _table.asStateFlow()

    private val _isSuccessGetRoomInfo = MutableStateFlow(false)
    val isSuccessGetRoomInfo: StateFlow<Boolean> = _isSuccessGetRoomInfo.asStateFlow()

    private val _roomInfo = MutableStateFlow(RoomInfo())
    val roomInfo: StateFlow<RoomInfo> = _roomInfo.asStateFlow()

    private val _useBrushEvent = MutableSharedFlow<UiEvent>()
    val useBrushEvent: SharedFlow<UiEvent> = _useBrushEvent.asSharedFlow()

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
        if (window.value.isCleanable) _showWindowBrush.value = newShow
        viewModelScope.launch { _clickedWindow.emit(true) }
    }

    fun initShowBedBrush() {
        val newShow = !requireNotNull(showBedBrush.value)
        initAllRoomFurniture()
        if (bed.value.isCleanable) _showBedBrush.value = newShow
        viewModelScope.launch { _clickedBed.emit(true) }
    }

    fun initShowTableBrush() {
        val newShow = !requireNotNull(showTableBrush.value)
        initAllRoomFurniture()
        if (table.value.isCleanable) _showTableBrush.value = newShow
        viewModelScope.launch { _clickedTable.emit(true) }
    }

    private fun initFurnitureInfo(furnitureList: List<Furniture>) {
        for (furniture in furnitureList) {
            when (furniture.furnitureName) {
                FurnitureType.BASIC_WALL -> continue
                FurnitureType.BASIC_WINDOW -> {
                    _window.value = furniture
                }
                FurnitureType.BASIC_BED -> {
                    _bed.value = furniture
                }
                FurnitureType.BASIC_TABLE -> {
                    _table.value = furniture
                }
            }
        }
    }

    fun getRoom() {
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

    fun putFurnitureClean(furnitureId: Int) {
        viewModelScope.launch {
            _useBrushEvent.emit(UiEvent.LOADING)
            roomRepository.putFurnitureClean(furnitureId)
                .onSuccess { response ->
                    initFurnitureInfo(response.furnitureList)
                    _roomInfo.value = response
                    _useBrushEvent.emit(UiEvent.SUCCESS)
                }
                .onFailure { throwable ->
                    Timber.d(throwable.message.toString())
                    if (throwable is HttpException) {
                        when (throwable.code()) {
                            ZERO_BRUSH -> _useBrushEvent.emit(UiEvent.ERROR)
                        }
                    }
                }
        }
    }

    companion object {
        const val ZERO_BRUSH = 403
    }
}
