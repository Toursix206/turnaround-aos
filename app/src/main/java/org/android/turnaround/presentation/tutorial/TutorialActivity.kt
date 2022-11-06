package org.android.turnaround.presentation.tutorial

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.data.remote.service.KakaoLoginService
import org.android.turnaround.databinding.ActivityTutorialBinding
import org.android.turnaround.domain.entity.Tutorial
import org.android.turnaround.presentation.main.MainActivity
import org.android.turnaround.presentation.signup.SignUpActivity
import org.android.turnaround.presentation.tutorial.TutorialViewModel.Companion.DUPLICATE_LOGIN
import org.android.turnaround.presentation.tutorial.TutorialViewModel.Companion.NOT_USER
import org.android.turnaround.presentation.tutorial.TutorialViewModel.Companion.NOT_VALID_SOCIAL_TOKEN
import org.android.turnaround.presentation.tutorial.apdater.TutorialAdapter
import org.android.turnaround.util.binding.BindingActivity
import org.android.turnaround.util.extension.repeatOnStarted
import org.android.turnaround.util.showToast
import javax.inject.Inject

@AndroidEntryPoint
class TutorialActivity : BindingActivity<ActivityTutorialBinding>(R.layout.activity_tutorial) {
    @Inject
    lateinit var kakaoLoginService: KakaoLoginService
    private val viewModel by viewModels<TutorialViewModel>()
    private val tutorialAdapter = TutorialAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initTutorialViewPager()
        initTutorialViewPagerSelectedListener()
        initKakaoLoginClickListener()
        initCurrentTutorialCollector()
        initIsSuccessKakaoLoginCollector()
        initSuccessLoginCollector()
    }

    private fun initTutorialViewPager() {
        binding.vpTutorial.adapter = tutorialAdapter
        binding.dotsTutorial.attachTo(binding.vpTutorial)
        tutorialAdapter.submitList(tutorialItems)
    }

    private fun initTutorialViewPagerSelectedListener() {
        binding.vpTutorial.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.initCurrentTutorial(position)
            }
        })
    }

    private fun initKakaoLoginClickListener() {
        binding.btnTutorialKakaoLogin.setOnClickListener {
            kakaoLoginService.startKakaoLogin(viewModel.kakaoLoginCallback)
        }
    }

    private fun initCurrentTutorialCollector() {
        repeatOnStarted {
            viewModel.currentTutorial.collect { currentTutorial ->
                binding.vpTutorial.currentItem = currentTutorial
            }
        }
    }

    private fun initIsSuccessKakaoLoginCollector() {
        repeatOnStarted {
            viewModel.isSuccessKakaoLogin.collect { isSuccess ->
                if (isSuccess) {
                    viewModel.postLogin()
                }
            }
        }
    }

    private fun initSuccessLoginCollector() {
        repeatOnStarted {
            viewModel.isSuccessLogin.collect { isSuccess ->
                if (isSuccess) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    when (viewModel.failLoginStatusCode) {
                        NOT_VALID_SOCIAL_TOKEN -> {
                            showToast(getString(R.string.not_valid_social_token_error))
                        }
                        NOT_USER -> {
                            startActivity(Intent(this, SignUpActivity::class.java))
                            finish()
                        }
                        DUPLICATE_LOGIN -> {
                            // TODO 강제로그아웃 팝업 띄우는 로직 실행
                        }
                    }
                }
            }
        }
    }

    companion object {
        val tutorialItems = listOf(
            Tutorial(0, R.drawable.img_tutorial_1, R.string.tutorial_1),
            Tutorial(1, R.drawable.img_tutorial_2, R.string.tutorial_2),
            Tutorial(2, R.drawable.img_tutorial_3, R.string.tutorial_3)
        )
    }
}
