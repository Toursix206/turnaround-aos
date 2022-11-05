package org.android.turnaround

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.HiltAndroidApp
import org.android.turnaround.util.TurnAroundDebugTree
import timber.log.Timber

@HiltAndroidApp
class TurnAroundApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(TurnAroundDebugTree())
        // Kakao SDK 초기화
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
        // Kakao 키 해시 호출하기
        Timber.d(Utility.getKeyHash(this))
    }
}
