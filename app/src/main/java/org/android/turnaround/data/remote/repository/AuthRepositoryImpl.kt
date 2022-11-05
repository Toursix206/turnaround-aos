package org.android.turnaround.data.remote.repository

import org.android.turnaround.data.local.LocalAuthPrefDataSource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val localAuthPrefDataSource: LocalAuthPrefDataSource
) : AuthRepository {
    override fun initKakaoToken(kakaoToken: String) {
        localAuthPrefDataSource.socialType = SOCIAL_TYPE_KAKAO
        localAuthPrefDataSource.kakaoToken = kakaoToken
    }

    companion object {
        const val SOCIAL_TYPE_KAKAO = "KAKAO"
    }
}
