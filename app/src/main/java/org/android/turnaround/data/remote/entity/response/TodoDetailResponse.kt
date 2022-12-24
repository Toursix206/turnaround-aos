package org.android.turnaround.data.remote.entity.response

import org.android.turnaround.domain.entity.TodoCategory
import org.android.turnaround.domain.entity.TodoDetail
import org.android.turnaround.domain.entity.TodoPushType

data class TodoDetailResponse(
    val activityId: Int,
    val broom: Int,
    val category: String,
    val name: String,
    val point: Int,
    val pushStatus: String,
    val rewardItem: String?,
    val type: String,
    val leftTime: String,
    val isAfterStartAt: Boolean
) {
    fun toTodoDetail(todoId: Int): TodoDetail =
        TodoDetail(
            todoId = todoId,
            activityId = this.activityId,
            broom = this.broom,
            category = this.category,
            name = this.name,
            point = this.point,
            pushStatus = TodoPushType.valueOf(this.pushStatus),
            rewardItem = this.rewardItem,
            type = this.type,
            categoryName = TodoCategory.valueOf(this.category).title,
            categoryImage = TodoCategory.valueOf(this.category).roundImgRes,
            leftTime = this.leftTime,
            isAfterStartAt = this.isAfterStartAt
        )
}
