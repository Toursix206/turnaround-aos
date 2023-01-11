package org.android.turnaround.data.remote.entity.response

import com.google.gson.annotations.SerializedName
import org.android.turnaround.domain.entity.My
import org.android.turnaround.domain.entity.ProfileType
import org.android.turnaround.domain.entity.StoreUrl

data class MyResponse(
    val profileType: ProfileType,
    val nickname: String,
    val point: Int,
    val csUrl: String,
    @SerializedName("storeUrlInfo")
    val storeUrl: StoreUrlEntity
) {
    fun toMy(): My =
        My(
            profileType = this.profileType,
            nickname = this.nickname,
            point = this.point,
            csUrl = this.csUrl,
            storeUrl = StoreUrl(this.storeUrl.aos)
        )
}

data class StoreUrlEntity(
    val aos: String
)
