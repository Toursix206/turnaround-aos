package org.android.turnaround.presentation.todoeventedit

import android.os.Bundle
import android.view.Gravity
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.ActivityTodoEventEditBinding
import org.android.turnaround.presentation.todoeventedit.adapter.TodoEventEditAdapter
import org.android.turnaround.util.EventObserver
import org.android.turnaround.util.TurnAroundToast
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
    lateinit var editBottomSheet: TodoReserveBottomSheet
    private var deletedTodoId = -1

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
            binding.rvTodoEventEdit.adapter = TodoEventEditAdapter(
                context = applicationContext,
                viewModel = viewModel
            ).apply {
                submitTodoEventEditList(it)
            }
        }
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
                                    deletedTodoId = id
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
            (binding.rvTodoEventEdit.adapter as TodoEventEditAdapter).deleteTodoItem(deletedTodoId)
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
        viewModel.editTodo.observe(this) {
            editBottomSheet.dismiss()
            TurnAroundToast.showToast(this, it as String, gravity = Gravity.TOP)
        }
    }

    private fun initEditTodoFailObserver() {
        viewModel.editTodoFail.observe(this) {
            TurnAroundToast.showToast(this, it as String, textColor = R.color.turnaround_alert, gravity = Gravity.TOP)
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
        binding.ivTodoEventEditToolBarTitle.setOnClickListener {
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
