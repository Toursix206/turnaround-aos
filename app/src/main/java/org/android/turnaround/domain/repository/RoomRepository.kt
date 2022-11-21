package org.android.turnaround.domain.repository

import org.android.turnaround.domain.entity.RoomInfo

interface RoomRepository {
    suspend fun getRoomInfo(): Result<RoomInfo>
    suspend fun putFurnitureClean(furnitureId: Int): Result<RoomInfo>
}
