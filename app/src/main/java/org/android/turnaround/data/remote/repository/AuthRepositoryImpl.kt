package org.android.turnaround.data.remote.repository

import org.android.turnaround.data.local.datasource.LocalAuthPrefDataSource
import org.android.turnaround.data.remote.datasource.AuthDataSource
import org.android.turnaround.domain.entity.Login
import org.android.turnaround.domain.entity.SignUp
import org.android.turnaround.domain.entity.Token
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val localAuthPrefDataSource: LocalAuthPrefDataSource,
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override fun checkIsUser(): Boolean =
        localAuthPrefDataSource.accessToken.isNotBlank()

    override fun initKakaoToken(kakaoToken: String) {
        localAuthPrefDataSource.socialType = SOCIAL_TYPE_KAKAO
        localAuthPrefDataSource.kakaoToken = kakaoToken
        // TODO 추후 fcmToken 불러온 후 따로 저장하는 로직 필요
        localAuthPrefDataSource.fcmToken = "dummyFCMToken"
    }

    override fun initTurnAroundToken(token: Token) {
        localAuthPrefDataSource.accessToken = token.accessToken
        localAuthPrefDataSource.refreshToken = token.refreshToken
    }

    override suspend fun postNicknameValid(nickname: String): Result<Boolean> =
        kotlin.runCatching { authDataSource.postNicknameValid(nickname) }
            .map { response -> response.success }

    override suspend fun postSignUp(
        nickname: String,
        profileType: String
    ): Result<SignUp> =
        kotlin.runCatching {
            authDataSource.postSignUp(
                nickname = nickname,
                profileType = profileType,
                fcmToken = localAuthPrefDataSource.fcmToken,
                socialType = localAuthPrefDataSource.socialType,
                token = localAuthPrefDataSource.kakaoToken
            )
        }.map { response ->
            response.data.toSignUp()
        }

    override suspend fun postLogin(): Result<Login> =
        kotlin.runCatching {
            authDataSource.postLogin(
                fcmToken = localAuthPrefDataSource.fcmToken,
                socialType = localAuthPrefDataSource.socialType,
                token = localAuthPrefDataSource.kakaoToken
            )
        }.map { response ->
            response.data.toLogin()
        }

    companion object {
        const val SOCIAL_TYPE_KAKAO = "KAKAO"
    }
}
