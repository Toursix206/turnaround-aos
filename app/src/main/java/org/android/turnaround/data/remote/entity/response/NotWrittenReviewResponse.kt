package org.android.turnaround.data.remote.entity.response

import org.android.turnaround.domain.entity.NotWrittenReview
import org.android.turnaround.domain.entity.Rating

data class NotWrittenReviewResponse(
    val imageUrl: String,
    val name: String,
    val doneDate: String,
    val point: Int,
    val rating: RatingResponse,
    val written: Boolean
) {
    fun toNotWrittenReview(): NotWrittenReview =
        NotWrittenReview(
            imageUrl = this.imageUrl,
            name = this.name,
            doneDate = this.doneDate,
            point = this.point,
            rating = Rating(
                score = this.rating.score
            ),
            written = this.written
        )
}

data class RatingResponse(
    val score: Int
)
