package org.android.turnaround.data.remote.entity.response

import com.google.gson.annotations.SerializedName
import org.android.turnaround.domain.entity.CleanLevel
import org.android.turnaround.domain.entity.Furniture
import org.android.turnaround.domain.entity.FurnitureType
import org.android.turnaround.domain.entity.RoomInfo

data class RoomInfoResponse(
    val broom: Int,
    val cleanScore: Int,
    val experience: Int,
    val level: Int,
    @SerializedName("interiorCount")
    val furnitureCount: Int,
    @SerializedName("interiors")
    val furnitureList: List<FurnitureEntity>
) {
    fun toRoomInfo(): RoomInfo =
        RoomInfo(
            broom = this.broom,
            cleanScore = this.cleanScore,
            experience = this.experience,
            level = this.level,
            furnitureCount = this.furnitureCount,
            furnitureList = this.furnitureList.map { furniture ->
                Furniture(
                    furnitureCleanLevel = CleanLevel.valueOf(furniture.furnitureCleanLevel),
                    furnitureName = FurnitureType.valueOf(furniture.furnitureName),
                    furnitureId = furniture.furnitureId,
                    isCleanable = furniture.isCleanable
                )
            }
        )
}

data class FurnitureEntity(
    @SerializedName("obtainId")
    val furnitureId: Int,
    @SerializedName("interiorName")
    val furnitureName: String,
    @SerializedName("interiorCleanLevel")
    val furnitureCleanLevel: String,
    val isCleanable: Boolean
)
