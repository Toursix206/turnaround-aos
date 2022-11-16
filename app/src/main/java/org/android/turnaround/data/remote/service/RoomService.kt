package org.android.turnaround.data.remote.service

import org.android.turnaround.data.remote.entity.response.BaseResponse
import org.android.turnaround.data.remote.entity.response.RoomInfoResponse
import retrofit2.http.GET

interface RoomService {
    @GET("/v1/space")
    suspend fun getRoomInfo(): BaseResponse<RoomInfoResponse>
}
