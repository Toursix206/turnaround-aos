package org.android.turnaround.util.bottom_sheet.todo_reserve

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.android.turnaround.domain.entity.PushStatusType

@Parcelize
class TodoReserveBtnClickListener(
    val clickAction: (PushStatusType, String) -> Unit
) : Parcelable {
    fun onConfirmClick(pushStatus: PushStatusType, startAt: String) {
        clickAction(pushStatus, startAt)
    }
}
