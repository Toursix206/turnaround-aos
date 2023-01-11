package org.android.turnaround.domain.entity

data class My(
    val profileType: ProfileType,
    val nickname: String,
    val point: Int,
    val csUrl: String,
    val storeUrl: StoreUrl
)

data class StoreUrl(
    val aos: String
)
