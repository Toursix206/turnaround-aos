package org.android.turnaround.util.dialog

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class DialogBtnClickListener(
    val id: Int = -1,
    val clickAction: () -> Unit = {},
    val clickActionWithId: (Int) -> Unit = {}
) : Parcelable {
    fun onConfirmClick() {
        if (id == -1) {
            clickAction()
        } else {
            clickActionWithId(id)
        }
    }
}
