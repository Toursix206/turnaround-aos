package org.android.turnaround.domain.entity

import org.android.turnaround.R

enum class TodoCategory(val title: String, val imgRes: Int, val roundImgRes: Int) {
    BEDDING("침구류", R.drawable.img_todo_bedding, R.drawable.img_todo_bedding_round),
    WASHER("세탁기", R.drawable.img_todo_washer, R.drawable.img_todo_washer_round),
    TABLE("책상", R.drawable.img_todo_table, R.drawable.img_todo_table_round),
    KITCHEN("주방", R.drawable.img_todo_kitchen, R.drawable.img_todo_kitchen_round),
    RESTROOM("화장실", R.drawable.img_todo_restroom, R.drawable.img_todo_restroom_round),
    SELF_DEVELOPMENT("자기계발", R.drawable.img_todo_self_development, R.drawable.img_todo_self_development_round),
    ETC("기타가구", R.drawable.img_todo_etc, R.drawable.img_todo_etc_round);
}
