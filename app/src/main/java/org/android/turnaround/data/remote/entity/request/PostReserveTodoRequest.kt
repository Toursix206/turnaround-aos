package org.android.turnaround.data.remote.entity.request

import org.android.turnaround.domain.entity.PushStatusType

data class PostReserveTodoRequest(
    val activityId: Int,
    val pushStatus: PushStatusType,
    val startAt: String // yyyy-MM-ddTHH:mm:ss (T는 문자 그대로)
)
