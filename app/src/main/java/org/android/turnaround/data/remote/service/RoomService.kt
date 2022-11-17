package org.android.turnaround.data.remote.service

import org.android.turnaround.data.remote.entity.response.BaseResponse
import org.android.turnaround.data.remote.entity.response.RoomInfoResponse
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface RoomService {
    @GET("/v1/space")
    suspend fun getRoomInfo(): BaseResponse<RoomInfoResponse>

    @PUT("/v1/interior/obtain/{obtainId}/clean")
    suspend fun putFurnitureClean(
        @Path("obtainId") furnitureId: Int
    ): BaseResponse<RoomInfoResponse>
}
