package org.android.turnaround.data.remote.entity.response

import org.android.turnaround.domain.entity.Activity
import org.android.turnaround.domain.entity.ActivityCategory
import org.android.turnaround.domain.entity.ActivityContent
import org.android.turnaround.domain.entity.ActivityType

data class ActivityResponse(
    val lastPage: Int,
    val nextPage: Int,
    val contents: List<ActivityContentEntity>
) {
    fun toActivityContent(): Activity = Activity(
        lastPage = this.lastPage,
        nextPage = this.nextPage,
        activityContents = this.contents.map { content ->
            ActivityContent(
                activityId = content.activityId,
                broom = content.broom,
                category = ActivityCategory.valueOf(content.category),
                type = ActivityType.valueOf(content.type),
                name = content.name,
                description = content.description,
                duration = content.duration,
                imageUrl = content.imageUrl
            )
        }
    )
}

data class ActivityContentEntity(
    val activityId: Int,
    val broom: Int,
    val category: String,
    val type: String,
    val name: String,
    val description: String,
    val duration: Int,
    val imageUrl: String
)
