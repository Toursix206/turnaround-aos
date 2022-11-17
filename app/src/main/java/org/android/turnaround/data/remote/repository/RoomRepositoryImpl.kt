package org.android.turnaround.data.remote.repository

import org.android.turnaround.data.remote.datasource.RoomDataSource
import org.android.turnaround.domain.entity.RoomInfo
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val roomDataSource: RoomDataSource
) : RoomRepository {
    override suspend fun getRoomInfo(): Result<RoomInfo> =
        kotlin.runCatching { roomDataSource.getRoomInfo() }
            .map { response -> response.data.toRoomInfo() }

    override suspend fun putFurnitureClean(furnitureId: Int): Result<RoomInfo> =
        kotlin.runCatching { roomDataSource.putFurnitureClean(furnitureId) }
            .map { response -> response.data.toRoomInfo() }
}
