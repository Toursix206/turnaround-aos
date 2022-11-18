package org.android.turnaround.data.remote.entity.response

import org.android.turnaround.domain.entity.TodoCategory
import org.android.turnaround.domain.entity.TodoDetail
import org.android.turnaround.domain.entity.TodoImageCategory

data class TodoDetailResponse(
    val activityId: Int,
    val broom: Int,
    val category: String,
    val name: String,
    val point: Int,
    val rewardItem: String?,
    val type: String
) {
    fun toTodoDetail(): TodoDetail =
        TodoDetail(
            activityId = this.activityId,
            broom = this.broom,
            category = this.category,
            name = this.name,
            point = this.point,
            rewardItem = this.rewardItem,
            type = this.type,
            categoryName = TodoCategory.valueOf(this.category).title,
            categoryImage = TodoImageCategory.valueOf(this.category).res
        )
}