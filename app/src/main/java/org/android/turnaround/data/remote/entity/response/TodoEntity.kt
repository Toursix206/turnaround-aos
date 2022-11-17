package org.android.turnaround.data.remote.entity.response

import org.android.turnaround.R
import org.android.turnaround.domain.entity.ActivityType

data class TodoEntity(
    val activityCategory: String,
    val activityName: String,
    val leftTime: String,
    val todoId: Int,
    val todoStatus: String
)

fun toCategoryImage(activityCategory: String): Int = when (activityCategory) {
    ActivityType.BEDDING.type -> R.drawable.img_todo_bedding
    ActivityType.TABLE.type -> R.drawable.img_todo_table
    ActivityType.KITCHEN.type -> R.drawable.img_todo_kitchen
    ActivityType.RESTROOM.type -> R.drawable.img_todo_restroom
    ActivityType.SELF_DEVELOPMENT.type -> R.drawable.img_todo_self_development
    ActivityType.ETC.type -> R.drawable.img_todo_etc
    ActivityType.WASHER.type -> R.drawable.img_todo_washer
    else -> R.drawable.img_todo_self_development
}

fun toCategoryRoundImage(activityCategory: String): Int = when (activityCategory) {
    ActivityType.BEDDING.type -> R.drawable.img_todo_bedding_round
    ActivityType.TABLE.type -> R.drawable.img_todo_table_round
    ActivityType.KITCHEN.type -> R.drawable.img_todo_kitchen_round
    ActivityType.RESTROOM.type -> R.drawable.img_todo_restroom_round
    ActivityType.SELF_DEVELOPMENT.type -> R.drawable.img_todo_self_development_round
    ActivityType.ETC.type -> R.drawable.img_todo_etc_round
    ActivityType.WASHER.type -> R.drawable.img_todo_washer_round
    else -> R.drawable.img_todo_self_development_round
}

fun toCategoryName(activityCategory: String): String = when (activityCategory) {
    ActivityType.BEDDING.type -> "침대"
    ActivityType.TABLE.type -> "책상"
    ActivityType.KITCHEN.type -> "주방"
    ActivityType.RESTROOM.type -> "화장실"
    ActivityType.SELF_DEVELOPMENT.type -> "자기계발"
    ActivityType.ETC.type -> "기타가구"
    ActivityType.WASHER.type -> "세탁기"
    else -> "기타가구"
}
