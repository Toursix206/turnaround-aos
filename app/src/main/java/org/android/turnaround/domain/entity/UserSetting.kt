package org.android.turnaround.domain.entity

data class UserSetting(
    val isAgreeNotification: Boolean,
    val openSourceLicenseUrl: OpenSourceLicenseUrl,
    val policyUrl: String
)

data class OpenSourceLicenseUrl(
    val aos: String
)
