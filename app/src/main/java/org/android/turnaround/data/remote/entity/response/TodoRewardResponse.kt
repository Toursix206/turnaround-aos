package org.android.turnaround.data.remote.entity.response

import org.android.turnaround.domain.entity.*

data class TodoRewardResponse(
    val broom: Int
) {
    fun toTodoReward(): TodoReward =
        TodoReward(
            broom = this.broom
        )
}
