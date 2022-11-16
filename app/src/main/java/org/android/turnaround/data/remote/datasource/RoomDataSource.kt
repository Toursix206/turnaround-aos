package org.android.turnaround.data.remote.datasource

import org.android.turnaround.data.remote.entity.response.BaseResponse
import org.android.turnaround.data.remote.entity.response.RoomInfoResponse
import org.android.turnaround.data.remote.service.RoomService
import javax.inject.Inject

class RoomDataSource @Inject constructor(
    private val roomService: RoomService
) {
    suspend fun getRoomInfo(): BaseResponse<RoomInfoResponse> =
        roomService.getRoomInfo()
}
