package org.android.turnaround.data.remote.entity.request

data class SignUpRequest(
    val nickname: String,
    val profileType: String,
    val fcmToken: String,
    val socialType: String,
    val token: String
)
