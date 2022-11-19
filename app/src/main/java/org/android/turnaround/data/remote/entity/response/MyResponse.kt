package org.android.turnaround.data.remote.entity.response

import org.android.turnaround.domain.entity.*

data class MyResponse(
    val profileType: ProfileType,
    val nickname: String,
    val point: Int
) {
    fun toMy(): My =
        My(
            profileType = this.profileType,
            nickname = this.nickname,
            point = this.point
        )
}
