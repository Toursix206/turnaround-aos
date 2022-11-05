package org.android.turnaround.data.remote.entity.response

import org.android.turnaround.domain.entity.SignUp
import org.android.turnaround.domain.entity.Token

data class SignUpResponse(
    val userId: Int,
    val token: TokenEntity
) {
    fun toSignUp(): SignUp =
        SignUp(
            userId = this.userId,
            token = Token(
                accessToken = this.token.accessToken,
                refreshToken = this.token.refreshToken
            )
        )
}
