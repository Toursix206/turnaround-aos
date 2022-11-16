package org.android.turnaround.data.remote.entity.response

import com.google.gson.annotations.SerializedName
import org.android.turnaround.domain.entity.Furniture
import org.android.turnaround.domain.entity.RoomInfo

data class RoomInfoResponse(
    val broom: Int,
    val cleanScore: Int,
    val experience: Int,
    val level: Int,
    @SerializedName("interiors")
    val furnitureList: List<FurnitureEntity>
) {
    fun toRoomInfo(): RoomInfo =
        RoomInfo(
            broom = this.broom,
            cleanScore = this.cleanScore,
            experience = this.experience,
            level = this.level,
            furnitureList = this.furnitureList.map { furniture ->
                Furniture(
                    furnitureCleanLevel = furniture.furnitureCleanLevel,
                    furnitureName = furniture.furnitureName,
                    furnitureId = furniture.furnitureId,
                    isCleanable = furniture.isCleanable
                )
            }
        )
}

data class FurnitureEntity(
    @SerializedName("interiorCleanLevel")
    val furnitureCleanLevel: String,
    @SerializedName("interiorName")
    val furnitureName: String,
    @SerializedName("obtainId")
    val furnitureId: Int,
    val isCleanable: Boolean
)
