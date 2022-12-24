package org.android.turnaround.presentation.my_todo

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
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
import org.android.turnaround.presentation.todo_guide.TodoGuideActivity
import org.android.turnaround.presentation.todo_guide.TodoGuideActivity.Companion.TODO_GUIDE_TODO_ID
import org.android.turnaround.util.ToastMessageUtil
import org.android.turnaround.util.UiEvent
import org.android.turnaround.util.binding.BindingActivity
import org.android.turnaround.util.bottom_sheet.todo_start.TodoStartBottomSheet
import org.android.turnaround.util.bottom_sheet.todo_start.TodoStartBottomSheet.Companion.BOTTOM_SHEET_TODO_START
import org.android.turnaround.util.bottom_sheet.todo_start.TodoStartBottomSheet.Companion.TODO_START_CONTENT
import org.android.turnaround.util.dialog.DialogBtnClickListener
import org.android.turnaround.util.extension.repeatOnStarted

@AndroidEntryPoint
class MyTodoActivity : BindingActivity<ActivityMyTodoBinding>(R.layout.activity_my_todo) {
    private val viewModel by viewModels<MyTodoViewModel>()
    private var todoStartBottomSheet = TodoStartBottomSheet()

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
        initStartTodoAbleEventCollector()
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

    private fun initStartTodoAbleEventCollector() {
        repeatOnStarted {
            viewModel.todoStartAbleEvent.collect { uiEvent ->
                when (uiEvent) {
                    UiEvent.SUCCESS -> {
                        todoStartBottomSheet.dismiss()
                        startActivity(
                            Intent(this, TodoGuideActivity::class.java).apply {
                                putExtra(TODO_GUIDE_TODO_ID, requireNotNull(viewModel.todoDetail.value).todoId)
                            }
                        )
                    }
                    UiEvent.ERROR -> {
                        ToastMessageUtil.showPurpleToast(this, getString(R.string.todo_reserve_toast_msg_duplicate), true, Gravity.TOP)
                    }
                    UiEvent.LOADING -> {}
                }
            }
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
        todoStartBottomSheet.apply {
            arguments = Bundle().apply {
                putParcelable(TODO_START_CONTENT, todoDetail)
                putParcelable(
                    TodoStartBottomSheet.CONFIRM_ACTION,
                    DialogBtnClickListener(id = todoDetail.todoId, clickActionWithId = { id -> viewModel.getTodoStartAble(id) })
                )
            }
        }.show(supportFragmentManager, BOTTOM_SHEET_TODO_START)
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
