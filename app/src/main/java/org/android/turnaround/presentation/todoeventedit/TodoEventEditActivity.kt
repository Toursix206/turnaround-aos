package org.android.turnaround.presentation.todoeventedit

import android.os.Bundle
import android.view.Gravity
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.ActivityTodoEventEditBinding
import org.android.turnaround.domain.entity.Todo
import org.android.turnaround.presentation.todoeventedit.adapter.TodoEventEditAdapter
import org.android.turnaround.util.*
import org.android.turnaround.util.binding.BindingActivity

@AndroidEntryPoint
class TodoEventEditActivity : BindingActivity<ActivityTodoEventEditBinding>(R.layout.activity_todo_event_edit) {
    private val viewModel by viewModels<TodoEventEditViewModel>()
    lateinit var editBottomSheet: TodoEditBottomSheet
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
                TurnAroundFragmentDialog.Builder(this)
                    .setTitle("진짜 활동을 삭제할까요?")
                    .setContent("실패한 활동으로 기록되지는 않아요")
                    .setNegativeButton("아니요")
                    .setPositiveButton("네! 삭제할게요") {
                        deletedTodoId = todoId
                        viewModel.deleteTodo(todoId)
                    }
                    .show(supportFragmentManager, this.packageCodePath)
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
                showTodoEditBottomSheet(it)
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

    private fun showTodoEditBottomSheet(todo: Todo) {
        editBottomSheet = TodoEditBottomSheet(viewModel, todo)
        editBottomSheet.show(supportFragmentManager, this.javaClass.name)
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
