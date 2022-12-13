package org.android.turnaround.domain.entity

data class Home(
    val nickname: String,
    val level: Int,
    val broom: Int,
    val cleanScore: Int,
    val todosCnt: Int,
    val todos: List<HomeTodo>
)
