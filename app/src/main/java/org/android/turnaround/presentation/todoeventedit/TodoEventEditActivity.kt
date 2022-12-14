package org.android.turnaround.presentation.todoeventedit

import android.os.Bundle
import android.view.Gravity
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.ActivityTodoEventEditBinding
import org.android.turnaround.domain.entity.TodoEvent
import org.android.turnaround.domain.entity.TodoHeader
import org.android.turnaround.domain.entity.TodoList
import org.android.turnaround.presentation.todoeventedit.adapter.TodoEditAdapter
import org.android.turnaround.util.EventObserver
import org.android.turnaround.util.ToastMessageUtil
import org.android.turnaround.util.binding.BindingActivity
import org.android.turnaround.util.bottom_sheet.TodoReserveBottomSheet
import org.android.turnaround.util.bottom_sheet.TodoReserveContent
import org.android.turnaround.util.bottom_sheet.TodoReserveType
import org.android.turnaround.util.dialog.DialogBtnClickListener
import org.android.turnaround.util.dialog.WarningDialogFragment
import org.android.turnaround.util.dialog.WarningType

@AndroidEntryPoint
class TodoEventEditActivity : BindingActivity<ActivityTodoEventEditBinding>(R.layout.activity_todo_event_edit) {
    private val viewModel by viewModels<TodoEventEditViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initTodoListObserver()
        initIsCheckedDeleteBtnEventObserver()
        initDeleteTodoObserver()
        initIsClickedEditBtnEventObserver()
        initBackBtnClickListener()
        initEditTodoObserver()
        initEditTodoFailObserver()

        this.onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun initTodoListObserver() {
        viewModel.todoList.observe(this) {
            binding.rvTodoEventEdit.adapter = TodoEditAdapter(
                viewModel = viewModel
            ).apply {
                submitList(getTodoList(it))
            }
        }
    }

    private fun getTodoList(list: TodoList): MutableList<TodoEvent> {
        val data = mutableListOf<TodoEvent>()
        // 오늘의 활동
        if (list.todayTodosCnt > 0) {
            val header = TodoHeader(getString(R.string.todo_event_todo_today), list.todayTodosCnt)
            data.add(header)
            data.addAll(list.todayTodos)
        }
        // 이번주 활동
        if (list.thisWeekTodosCnt > 0) {
            val header = TodoHeader(getString(R.string.todo_event_todo_this_week), list.thisWeekTodosCnt)
            data.add(header)
            data.addAll(list.thisWeekTodos)
        }
        // 다음 활동
        if (list.nextTodosCnt > 0) {
            val header = TodoHeader(getString(R.string.todo_event_todo_next), list.nextTodosCnt)
            data.add(header)
            data.addAll(list.nextTodos)
        }
        return data
    }

    private fun initIsCheckedDeleteBtnEventObserver() {
        viewModel.isClickedDeleteBtnEvent.observe(
            this,
            EventObserver { todoId ->
                WarningDialogFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(
                            WarningDialogFragment.WARNING_TYPE,
                            WarningType.WARNING_DELETE_ACTIVITY
                        )
                        putParcelable(
                            WarningDialogFragment.CONFIRM_ACTION,
                            DialogBtnClickListener(
                                id = todoId,
                                clickActionWithId = { id ->
                                    viewModel.deleteTodo(id)
                                }
                            )
                        )
                    }
                }.show(supportFragmentManager, WarningDialogFragment.DIALOG_WARNING)
            }
        )
    }

    private fun initDeleteTodoObserver() {
        viewModel.deleteTodo.observe(this) {
            viewModel.getTodoList()
        }
    }

    private fun initIsClickedEditBtnEventObserver() {
        viewModel.isClickedEditBtnEvent.observe(
            this,
            EventObserver {
                showTodoEditBottomSheet()
            }
        )
    }

    private fun initEditTodoObserver() {
        viewModel.editTodo.observe(this) { editTodo ->
            ToastMessageUtil.showPurpleToast(this, editTodo, false, gravity = Gravity.TOP)
            viewModel.getTodoList()
        }
    }

    private fun initEditTodoFailObserver() {
        viewModel.editTodoFail.observe(this) { editTodoFail ->
            ToastMessageUtil.showPurpleToast(this, editTodoFail, true, gravity = Gravity.TOP)
        }
    }

    private fun showTodoEditBottomSheet() {
        val todoReserveContent = requireNotNull(viewModel.isClickedEditBtnEvent.value).peekContent()
        TodoReserveBottomSheet().apply {
            arguments = Bundle().apply {
                putSerializable(TodoReserveBottomSheet.RESERVE_TYPE, TodoReserveType.EDIT_MODE)
                putParcelable(
                    TodoReserveBottomSheet.RESERVE_CONTENT,
                    TodoReserveContent(
                        id = todoReserveContent.todoId,
                        duration = todoReserveContent.duration,
                        pushStatus = todoReserveContent.pushStatus
                    )
                )
            }
        }.show(supportFragmentManager, TodoReserveBottomSheet.BOTTOM_SHEET_RESERVE)
    }

    private fun initBackBtnClickListener() {
        binding.ivTodoEventEditBack.setOnClickListener {
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}
