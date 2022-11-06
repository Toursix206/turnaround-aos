package org.android.turnaround.data.remote.entity.request

data class LoginRequest(
    val fcmToken: String,
    val socialType: String,
    val token: String
)
