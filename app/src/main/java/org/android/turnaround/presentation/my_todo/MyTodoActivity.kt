package org.android.turnaround.presentation.my_todo

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.ActivityMyTodoBinding
import org.android.turnaround.domain.entity.TodoDetail
import org.android.turnaround.presentation.main.MainActivity
import org.android.turnaround.presentation.main.MainActivity.Companion.MOVE_TO_ACTIVITY_TAB
import org.android.turnaround.presentation.my_todo.adaprer.MyTodoAdapter
import org.android.turnaround.presentation.todo_edit.TodoEditActivity
import org.android.turnaround.util.ToastMessageUtil
import org.android.turnaround.util.binding.BindingActivity
import org.android.turnaround.util.bottom_sheet.todo_start.TodoStartBottomSheet
import org.android.turnaround.util.bottom_sheet.todo_start.TodoStartBottomSheet.Companion.TODO_START_CONTENT

@AndroidEntryPoint
class MyTodoActivity : BindingActivity<ActivityMyTodoBinding>(R.layout.activity_my_todo) {
    private val viewModel by viewModels<MyTodoViewModel>()

    private val todoEditResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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
        initGoActivityListener()
        this.onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun initTodoListObserver() {
        viewModel.todoList.observe(this) {
            binding.rvTodoEvent.adapter = MyTodoAdapter(
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
            ToastMessageUtil.showGrayToast(this@MyTodoActivity, it as String)
        }
    }

    private fun initTodoEventEditClickListener() {
        binding.ivTodoEventSetting.setOnClickListener {
            todoEditResultLauncher.launch(Intent(this, TodoEditActivity::class.java))
        }
    }

    private fun initBackBtnClickListener() {
        binding.ivTodoEventBack.setOnClickListener {
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun initGoActivityListener() {
        binding.btnTodoEventGoActivity.setOnClickListener {
            startActivity(
                Intent(this, MainActivity::class.java).apply {
                    putExtra(MOVE_TO_ACTIVITY_TAB, true)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION)
                }
            )
        }
    }

    private fun showTodoStartBottomSheet(todoDetail: TodoDetail) {
        TodoStartBottomSheet().apply {
            arguments = Bundle().apply {
                putParcelable(TODO_START_CONTENT, todoDetail)
            }
        }.show(supportFragmentManager, this.javaClass.name)
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

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}
