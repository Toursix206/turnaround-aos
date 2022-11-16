package org.android.turnaround.domain.entity

data class RoomInfo(
    val broom: Int? = -1,
    val cleanScore: Int? = -1,
    val experience: Int? = -1,
    val level: Int? = -1,
    val furnitureList: List<Furniture> = emptyList()
)

data class Furniture(
    val furnitureCleanLevel: String = "",
    val furnitureName: String = "",
    val furnitureId: Int = -1,
    val isCleanable: Boolean = false
)
