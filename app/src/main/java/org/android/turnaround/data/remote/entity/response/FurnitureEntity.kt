package org.android.turnaround.data.remote.entity.response

import com.google.gson.annotations.SerializedName

data class FurnitureEntity(
    @SerializedName("obtainId")
    val furnitureId: Int,
    @SerializedName("interiorName")
    val furnitureName: String,
    @SerializedName("interiorCleanLevel")
    val furnitureCleanLevel: String,
    val isCleanable: Boolean
)
