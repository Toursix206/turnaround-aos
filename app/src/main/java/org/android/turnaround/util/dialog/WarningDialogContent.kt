package org.android.turnaround.util.dialog

import android.content.Context
import org.android.turnaround.R

data class WarningDialogContent(
    val title: String = "",
    val desc: String = "",
    val cancel: String = "",
    val confirm: String = ""
) {
    fun getWarningDeleteActivity(context: Context): WarningDialogContent =
        WarningDialogContent(
            title = context.getString(R.string.warning_delete_activity_title),
            desc = context.getString(R.string.warning_delete_activity_desc),
            cancel = context.getString(R.string.warning_delete_activity_cancel),
            confirm = context.getString(R.string.warning_delete_activity_title)
        )

    fun getWarningCancelActivity(context: Context): WarningDialogContent =
        WarningDialogContent(
            title = context.getString(R.string.warning_cancel_activity_title),
            desc = context.getString(R.string.warning_cancel_activity_desc),
            cancel = context.getString(R.string.warning_cancel_activity_cancel),
            confirm = context.getString(R.string.warning_cancel_activity_title)
        )

    fun getWarningTakePhotoAgain(context: Context): WarningDialogContent =
        WarningDialogContent(
            title = context.getString(R.string.warning_take_photo_again_title),
            desc = context.getString(R.string.warning_take_photo_again_desc),
            cancel = context.getString(R.string.warning_take_photo_again_cancel),
            confirm = context.getString(R.string.warning_take_photo_again_title)
        )

    fun getWarningSkipReview(context: Context): WarningDialogContent =
        WarningDialogContent(
            title = context.getString(R.string.warning_skip_review_title),
            desc = context.getString(R.string.warning_skip_review_desc),
            cancel = context.getString(R.string.warning_skip_review_cancel),
            confirm = context.getString(R.string.warning_skip_review_title)
        )

    fun getWarningCancelReview(context: Context): WarningDialogContent =
        WarningDialogContent(
            title = context.getString(R.string.warning_cancel_review_title),
            desc = context.getString(R.string.warning_cancel_review_desc),
            cancel = context.getString(R.string.warning_cancel_review_cancel),
            confirm = context.getString(R.string.warning_cancel_review_title)
        )

    fun getWarningWithdraw(context: Context): WarningDialogContent =
        WarningDialogContent(
            title = context.getString(R.string.warning_withdraw_title),
            desc = context.getString(R.string.warning_withdraw_desc),
            cancel = context.getString(R.string.warning_withdraw_cancel),
            confirm = context.getString(R.string.warning_withdraw_title)
        )
}
