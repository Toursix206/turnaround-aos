package org.android.turnaround.data.remote.entity.response

import org.android.turnaround.domain.entity.Guide
import org.android.turnaround.domain.entity.TodoGuide

data class TodoGuideResponse(
    val activityId: Int,
    val name: String,
    val guides: List<GuideEntity>
) {
    fun toTodoGuide(): TodoGuide =
        TodoGuide(
            activityId = this.activityId,
            name = this.name,
            guides = this.guides.map { guide ->
                Guide(
                    step = guide.step,
                    title = guide.title,
                    content = guide.content ?: "",
                    imageUrl = guide.imageUrl
                )
            }
        )
}

data class GuideEntity(
    val step: Int,
    val title: String,
    val content: String?,
    val imageUrl: String
)
