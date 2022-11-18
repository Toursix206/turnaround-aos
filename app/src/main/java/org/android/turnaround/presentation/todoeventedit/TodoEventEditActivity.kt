package org.android.turnaround.presentation.todoeventedit

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.ActivityTodoEventEditBinding
import org.android.turnaround.domain.entity.Todo
import org.android.turnaround.presentation.todoeventedit.adapter.TodoEventEditAdapter
import org.android.turnaround.util.EventObserver
import org.android.turnaround.util.binding.BindingActivity
import org.android.turnaround.util.showToast

@AndroidEntryPoint
class TodoEventEditActivity : BindingActivity<ActivityTodoEventEditBinding>(R.layout.activity_todo_event_edit) {
    private val viewModel by viewModels<TodoEventEditViewModel>()
    lateinit var editBottomSheet: TodoEditBottomSheet

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
            EventObserver {
                viewModel.deleteTodo(it)
            }
        )
    }

    private fun initDeleteTodoObserver() {
        viewModel.deleteTodo.observe(this) {
            applicationContext.showToast("삭제됨! - TODO 삭제 확인 다이얼로그 추가해야함")
            refresh()
        }
    }

    private fun refresh() {
        viewModel.getTodoList()
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
            // Todo: 토스트 띄우기
            applicationContext.showToast(it as String)
            finish()
        }
    }

    private fun initEditTodoFailObserver() {
        viewModel.editTodoFail.observe(this) {
            // Todo: 토스트 띄우기
            applicationContext.showToast(it as String)
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
