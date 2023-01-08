package org.android.turnaround.data.remote.entity.response

import org.android.turnaround.domain.entity.Review

data class NotWrittenReviewResponse(
    val imageUrl: String,
    val name: String,
    val doneDate: String,
    val point: Int,
    val score: Int,
    val written: Boolean
) {
    fun toReview(): Review =
        Review(
            imageUrl = this.imageUrl,
            name = this.name,
            doneDate = this.doneDate,
            point = this.point,
            score = this.score,
            written = this.written
        )
}
