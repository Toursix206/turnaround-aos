package org.android.turnaround.domain.entity

data class Furniture(
    val furnitureId: Int = -1,
    val furnitureName: FurnitureType = FurnitureType.BASIC_WINDOW,
    val furnitureCleanLevel: CleanLevel = CleanLevel.CLEAN,
    val isCleanable: Boolean = false
)
