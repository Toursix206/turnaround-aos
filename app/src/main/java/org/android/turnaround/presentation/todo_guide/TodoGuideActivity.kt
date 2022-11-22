package org.android.turnaround.presentation.todo_guide

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.ActivityTodoGuideBinding
import org.android.turnaround.presentation.todo_guide.adapter.TodoGuideAdapter
import org.android.turnaround.util.binding.BindingActivity
import org.android.turnaround.util.extension.repeatOnStarted

@AndroidEntryPoint
class TodoGuideActivity : BindingActivity<ActivityTodoGuideBinding>(R.layout.activity_todo_guide) {
    private val viewModel by viewModels<TodoGuideViewModel>()
    private val todoGuideAdapter = TodoGuideAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        viewModel.getTodoGuide(1)
        initGuideImgViewPager()
        initGuidesCollector()
        initCurrentStepCollector()
    }

    private fun initGuideImgViewPager() {
        with(binding.vpTodoGuide) {
            adapter = todoGuideAdapter
            isUserInputEnabled = false
        }
    }

    private fun initGuidesCollector() {
        repeatOnStarted {
            viewModel.guides.collect { guides ->
                todoGuideAdapter.submitList(guides)
            }
        }
    }

    private fun initCurrentStepCollector() {
        repeatOnStarted {
            viewModel.currentStep.collect { step ->
                binding.vpTodoGuide.currentItem = step - 1
            }
        }
    }
}
