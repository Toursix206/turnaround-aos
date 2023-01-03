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
            confirm = context.getString(R.string.warning_delete_activity_confirm)
        )

    fun getWarningCancelActivity(context: Context): WarningDialogContent =
        WarningDialogContent(
            title = context.getString(R.string.warning_cancel_activity_title),
            desc = context.getString(R.string.warning_cancel_activity_desc),
            cancel = context.getString(R.string.warning_cancel_activity_cancel),
            confirm = context.getString(R.string.warning_cancel_activity_confirm)
        )

    fun getWarningTakePhotoAgain(context: Context): WarningDialogContent =
        WarningDialogContent(
            title = context.getString(R.string.warning_take_photo_again_title),
            desc = context.getString(R.string.warning_take_photo_again_desc),
            cancel = context.getString(R.string.warning_take_photo_again_cancel),
            confirm = context.getString(R.string.warning_take_photo_again_confirm)
        )

    fun getWarningSkipReview(context: Context): WarningDialogContent =
        WarningDialogContent(
            title = context.getString(R.string.warning_skip_review_title),
            desc = context.getString(R.string.warning_skip_review_desc),
            cancel = context.getString(R.string.warning_skip_review_cancel),
            confirm = context.getString(R.string.warning_skip_review_confirm)
        )

    fun getWarningWithdraw(context: Context): WarningDialogContent =
        WarningDialogContent(
            title = context.getString(R.string.warning_withdraw_title),
            desc = context.getString(R.string.warning_withdraw_desc),
            cancel = context.getString(R.string.warning_withdraw_cancel),
            confirm = context.getString(R.string.warning_withdraw_confirm)
        )

    fun getWarningNotification(context: Context): WarningDialogContent =
        WarningDialogContent(
            title = context.getString(R.string.warning_notification_title),
            desc = context.getString(R.string.warning_notification_desc),
            cancel = context.getString(R.string.warning_notification_cancel),
            confirm = context.getString(R.string.warning_notification_confirm)
        )

    fun getWarningDuplicateLogin(context: Context): WarningDialogContent =
        WarningDialogContent(
            title = context.getString(R.string.warning_duplicate_login_title),
            desc = context.getString(R.string.warning_duplicate_login_desc),
            cancel = context.getString(R.string.warning_duplicate_login_cancel),
            confirm = context.getString(R.string.warning_duplicate_login_confirm)
        )
}
