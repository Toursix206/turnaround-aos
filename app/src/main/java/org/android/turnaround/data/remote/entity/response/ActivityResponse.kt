package org.android.turnaround.data.remote.entity.response

import org.android.turnaround.domain.entity.Activity
import org.android.turnaround.domain.entity.Content

data class ActivityResponse(
    val lastPage: Int,
    val nextPage: Int,
    val contents: List<ContentEntity>
) {
    fun toActivity(): Activity = Activity(
        lastPage = this.lastPage,
        nextPage = this.nextPage,
        contents = this.contents.map { content ->
            Content(
                activityId = content.activityId,
                broom = content.broom,
                category = content.category,
                type = content.type,
                name = content.name,
                description = content.description,
                duration = content.duration,
                imageUrl = content.imageUrl
            )
        }
    )
}

data class ContentEntity(
    val activityId: Int,
    val broom: Int,
    val category: String,
    val type: String,
    val name: String,
    val description: String,
    val duration: Int,
    val imageUrl: String
)
