package org.android.turnaround.presentation.tutorial

import android.os.Bundle
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.data.remote.service.KakaoLoginService
import org.android.turnaround.databinding.ActivityTutorialBinding
import org.android.turnaround.util.binding.BindingActivity
import org.android.turnaround.util.extension.repeatOnStarted
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
        initSkipClickListener()
        initKakaoLoginClickListener()
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

    private fun initSkipClickListener() {
        repeatOnStarted {
            viewModel.currentTutorial.collect { currentTutorial ->
                binding.vpTutorial.currentItem = currentTutorial
            }
        }
    }

    private fun initKakaoLoginClickListener() {
        binding.btnTutorialKakaoLogin.setOnClickListener {
            kakaoLoginService.startKakaoLogin(viewModel.kakaoLoginCallback)
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
