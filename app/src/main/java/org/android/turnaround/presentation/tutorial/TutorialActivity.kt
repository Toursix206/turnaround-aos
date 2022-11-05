package org.android.turnaround.presentation.tutorial

import android.os.Bundle
import org.android.turnaround.R
import org.android.turnaround.databinding.ActivityTutorialBinding
import org.android.turnaround.util.binding.BindingActivity

class TutorialActivity : BindingActivity<ActivityTutorialBinding>(R.layout.activity_tutorial) {
    private val tutorialAdapter = TutorialAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initTutorialViewPager()
    }

    private fun initTutorialViewPager() {
        binding.vpTutorial.adapter = tutorialAdapter
        binding.dotsTutorial.attachTo(binding.vpTutorial)
        tutorialAdapter.submitList(tutorialItems)
    }

    companion object {
        val tutorialItems = listOf(
            Tutorial(0, R.drawable.img_tutorial_1, R.string.tutorial_1),
            Tutorial(1, R.drawable.img_tutorial_2, R.string.tutorial_2),
            Tutorial(2, R.drawable.img_tutorial_3, R.string.tutorial_3)
        )
    }
}
