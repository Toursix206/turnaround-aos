package org.android.turnaround.data.remote.entity.request

import org.android.turnaround.domain.entity.PushStatusType

data class TodoEditRequest(
    val pushStatus: PushStatusType,
    val startAt: String
)
