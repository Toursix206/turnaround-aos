package org.android.turnaround.data.remote.repository

import org.android.turnaround.data.local.datasource.LocalAuthPrefDataSource
import org.android.turnaround.data.remote.datasource.AuthDataSource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val localAuthPrefDataSource: LocalAuthPrefDataSource,
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override fun initKakaoToken(kakaoToken: String) {
        localAuthPrefDataSource.socialType = SOCIAL_TYPE_KAKAO
        localAuthPrefDataSource.kakaoToken = kakaoToken
    }

    override suspend fun postNicknameValid(nickname: String): Result<Boolean> =
        kotlin.runCatching { authDataSource.postNicknameValid(nickname) }
            .map { response -> response.success }

    companion object {
        const val SOCIAL_TYPE_KAKAO = "KAKAO"
    }
}
