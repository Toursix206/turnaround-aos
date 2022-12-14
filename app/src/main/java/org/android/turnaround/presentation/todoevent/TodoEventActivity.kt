package org.android.turnaround.presentation.todoevent

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.ActivityTodoEventBinding
import org.android.turnaround.domain.entity.TodoDetail
import org.android.turnaround.presentation.home.TodoStartBottomSheet
import org.android.turnaround.presentation.todoevent.adaprer.TodoEventAdapter
import org.android.turnaround.presentation.todoeventedit.TodoEventEditActivity
import org.android.turnaround.util.ToastMessageUtil
import org.android.turnaround.util.binding.BindingActivity

@AndroidEntryPoint
class TodoEventActivity : BindingActivity<ActivityTodoEventBinding>(R.layout.activity_todo_event) {
    private val viewModel by viewModels<TodoEventViewModel>()

    private val todoEventEditResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            refresh()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initTodoListObserver()
        initTodoDetailObserver()
        initTodoRewardObserver()
        initTodoAlarmOffObserver()
        initTodoEventEditClickListener()
        initBackBtnClickListener()
    }

    private fun initTodoListObserver() {
        viewModel.todoList.observe(this) {
            binding.rvTodoEvent.adapter = TodoEventAdapter(
                context = applicationContext,
                viewModel = viewModel
            ).apply {
                submitTodoEventList(it)
            }
        }
    }

    private fun initTodoRewardObserver() {
        viewModel.todoReward.observe(this) {
            showTodoDoneDialog(it.broom)
        }
    }

    private fun initTodoDetailObserver() {
        viewModel.todoDetail.observe(this) {
            showTodoStartBottomSheet(it)
        }
    }

    private fun initTodoAlarmOffObserver() {
        viewModel.alarmOff.observe(this) {
            ToastMessageUtil.showToast(this@TodoEventActivity, it as String)
        }
    }

    private fun initTodoEventEditClickListener() {
        binding.ivTodoEventSetting.setOnClickListener {
            todoEventEditResultLauncher.launch(Intent(this, TodoEventEditActivity::class.java))
        }
    }

    private fun initBackBtnClickListener() {
        binding.ivTodoEventBack.setOnClickListener {
            finish()
        }
    }

    private fun showTodoStartBottomSheet(todoDetail: TodoDetail) {
        TodoStartBottomSheet(todoDetail).show(supportFragmentManager, this.javaClass.name)
    }

    private fun showTodoDoneDialog(broomCount: Int) {
        TodoDoneDialogFragment().apply {
            arguments = Bundle().apply {
                putSerializable(
                    TodoDoneDialogFragment.BROOM_COUNT, broomCount
                )
            }
        }.show(supportFragmentManager, TodoDoneDialogFragment.DIALOG_TODO_DONE)
    }

    private fun refresh() {
        viewModel.getTodoList()
    }
}
