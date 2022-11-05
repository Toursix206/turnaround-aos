package org.android.turnaround.data.remote.repository

import org.android.turnaround.domain.entity.SignUp

interface AuthRepository {
    fun initKakaoToken(kakaoToken: String)

    suspend fun postNicknameValid(nickname: String): Result<Boolean>

    suspend fun postSignUp(nickname: String, profileType: String): Result<SignUp>
}
