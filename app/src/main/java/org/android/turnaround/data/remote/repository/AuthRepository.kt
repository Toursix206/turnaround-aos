package org.android.turnaround.data.remote.repository

interface AuthRepository {
    fun initKakaoToken(kakaoToken: String)

    suspend fun postNicknameValid(nickname: String): Result<Boolean>
}
