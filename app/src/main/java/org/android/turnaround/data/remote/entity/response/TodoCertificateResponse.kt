package org.android.turnaround.data.remote.entity.response

import org.android.turnaround.domain.entity.TodoCertificate

data class TodoCertificateResponse(
    val doneReviewId: Int
) {
    fun toTodoCertificate(): TodoCertificate =
        TodoCertificate(
            doneReviewId = this.doneReviewId
        )
}
