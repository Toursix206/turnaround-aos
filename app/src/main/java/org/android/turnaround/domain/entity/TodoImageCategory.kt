package org.android.turnaround.domain.entity

import org.android.turnaround.R

enum class TodoImageCategory(val res: Int) {
    BEDDING(R.drawable.img_todo_bedding), WASHER(R.drawable.img_todo_washer),
    TABLE(R.drawable.img_todo_table), KITCHEN(R.drawable.img_todo_kitchen),
    RESTROOM(R.drawable.img_todo_restroom), SELF_DEVELOPMENT(R.drawable.img_todo_self_development),
    ETC(R.drawable.img_todo_etc);
}
