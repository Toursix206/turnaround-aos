package org.android.turnaround.domain.entity

data class RoomInfo(
    val broom: Int,
    val cleanScore: Int,
    val experience: Int,
    val level: Int,
    val furnitureList: List<Furniture>
)

data class Furniture(
    val furnitureCleanLevel: String,
    val furnitureName: String,
    val furnitureId: Int,
    val isCleanable: Boolean
)
