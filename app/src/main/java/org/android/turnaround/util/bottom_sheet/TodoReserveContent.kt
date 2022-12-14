package org.android.turnaround.util.bottom_sheet

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.android.turnaround.domain.entity.PushStatusType

@Parcelize
data class TodoReserveContent(
    val id: Int,
    val duration: Int,
    val pushStatus: PushStatusType
) : Parcelable
