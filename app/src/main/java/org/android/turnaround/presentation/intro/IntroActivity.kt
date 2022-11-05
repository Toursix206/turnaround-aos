package org.android.turnaround.presentation.intro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import org.android.turnaround.R
import org.android.turnaround.presentation.tutorial.TutorialActivity


class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        moveToTutorial()
    }

    private fun moveToTutorial() {
        // 추후 Lottie 보여주고 화면이동 시킬 것임
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            val intent = Intent(this, TutorialActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}
