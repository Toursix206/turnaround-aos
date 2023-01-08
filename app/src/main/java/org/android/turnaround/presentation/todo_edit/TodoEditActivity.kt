package org.android.turnaround.presentation.todo_edit

import android.os.Bundle
import android.view.Gravity
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.ActivityTodoEditBinding
import org.android.turnaround.domain.entity.TodoEvent
import org.android.turnaround.domain.entity.TodoHeader
import org.android.turnaround.domain.entity.TodoList
import org.android.turnaround.presentation.todo_edit.TodoEditViewModel.Companion.ERROR_CANNOT_DELETE
import org.android.turnaround.presentation.todo_edit.TodoEditViewModel.Companion.ERROR_DUPLICATE_TODO
import org.android.turnaround.presentation.todo_edit.TodoEditViewModel.Companion.ERROR_INVALID_TODO_DATE
import org.android.turnaround.presentation.todo_edit.adapter.TodoEditAdapter
import org.android.turnaround.util.EventObserver
import org.android.turnaround.util.ToastMessageUtil
import org.android.turnaround.util.binding.BindingActivity
import org.android.turnaround.util.bottom_sheet.todo_reserve.TodoReserveBottomSheet
import org.android.turnaround.util.bottom_sheet.todo_reserve.TodoReserveContent
import org.android.turnaround.util.bottom_sheet.todo_reserve.TodoReserveType
import org.android.turnaround.util.dialog.DialogBtnClickListener
import org.android.turnaround.util.dialog.WarningDialogFragment
import org.android.turnaround.util.dialog.WarningType

@AndroidEntryPoint
class TodoEditActivity : BindingActivity<ActivityTodoEditBinding>(R.layout.activity_todo_edit) {
    private val viewModel by viewModels<TodoEditViewModel>()
    private val todoEditAdapter by lazy { TodoEditAdapter(viewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initRvTodoEdit()
        initTodoListObserver()
        initIsCheckedDeleteBtnEventObserver()
        initDeleteTodoObserver()
        initIsClickedEditBtnEventObserver()
        initBackBtnClickListener()
        initEditTodoObserver()
        initEditTodoFailObserver()
    }

    private fun initRvTodoEdit() {
        binding.rvTodoEdit.adapter = todoEditAdapter
    }

    private fun initTodoListObserver() {
        viewModel.todoList.observe(this) { todoList ->
            todoEditAdapter.submitList(getTodoListWithHeader(todoList))
        }
    }

    private fun getTodoListWithHeader(list: TodoList): MutableList<TodoEvent> {
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
        viewModel.isEditTodoSuccess.observe(this) { isSuccess ->
            if (isSuccess) {
                ToastMessageUtil.showPurpleToast(
                    this, getString(R.string.todo_reserve_edit_toast_msg_success), false, Gravity.TOP
                )
                viewModel.getTodoList()
            }
        }
    }

    private fun initEditTodoFailObserver() {
        viewModel.editTodoErrorCode.observe(this) { editTodoErrorCode ->
            when (editTodoErrorCode) {
                ERROR_INVALID_TODO_DATE -> ToastMessageUtil.showPurpleToast(
                    this, viewModel.editTodoErrorMessage, true, Gravity.TOP
                )
                ERROR_CANNOT_DELETE -> ToastMessageUtil.showPurpleToast(
                    this, getString(R.string.todo_reserve_toast_msg_cannot_delete), true, Gravity.TOP
                )
                ERROR_DUPLICATE_TODO -> ToastMessageUtil.showPurpleToast(
                    this, getString(R.string.todo_reserve_toast_msg_duplicate), true, Gravity.TOP
                )
            }
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
        binding.ivTodoEditBack.setOnClickListener {
            finish()
        }
    }
}
