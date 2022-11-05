package org.android.turnaround.presentation.intro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.presentation.main.MainActivity
import org.android.turnaround.presentation.tutorial.TutorialActivity
import org.android.turnaround.util.extension.repeatOnStarted

@AndroidEntryPoint
class IntroActivity : AppCompatActivity() {
    private val viewModel by viewModels<IntroViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        viewModel.checkIsUser()
        initIsUserCollector()
    }

    private fun initIsUserCollector() {
        repeatOnStarted {
            viewModel.isUser.collect { isUser ->
                Handler(Looper.getMainLooper()).postDelayed(Runnable {
                    val intent = if (isUser) {
                        Intent(this, MainActivity::class.java)
                    } else {
                        Intent(this, TutorialActivity::class.java)
                    }
                    startActivity(intent)
                    finish()
                }, 2000)
            }
        }
    }
}
