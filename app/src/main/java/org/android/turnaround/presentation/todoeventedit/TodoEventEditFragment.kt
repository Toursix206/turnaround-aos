package org.android.turnaround.presentation.todoeventedit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentTodoEventEditBinding
import org.android.turnaround.domain.entity.Todo
import org.android.turnaround.presentation.todoeventedit.adapter.TodoEventEditAdapter
import org.android.turnaround.util.EventObserver
import org.android.turnaround.util.binding.BindingFragment
import timber.log.Timber

@AndroidEntryPoint
class TodoEventEditFragment : BindingFragment<FragmentTodoEventEditBinding>(R.layout.fragment_todo_event_edit) {
    private val viewModel by viewModels<TodoEventEditViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTodoListObserver()
        initIsCheckedDeleteBtnEventObserver()
        initDeleteTodoObserver()
        initIsClickedEditBtnEventObserver()
        initBackBtnClickListener()
        initEditTodoObserver()
    }

    private fun initTodoListObserver() {
        viewModel.todoList.observe(viewLifecycleOwner) {
            binding.rvTodoEventEdit.adapter = TodoEventEditAdapter(
                context = requireContext(),
                viewModel = viewModel
            ).apply {
                submitTodoEventEditList(it)
            }
        }
    }

    private fun initIsCheckedDeleteBtnEventObserver() {
        viewModel.isClickedDeleteBtnEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                viewModel.deleteTodo(it)
            }
        )
    }

    private fun initDeleteTodoObserver() {
        viewModel.deleteTodo.observe(viewLifecycleOwner) {
            refresh()
        }
    }

    private fun refresh() {
        viewModel.getTodoList()
    }

    private fun initIsClickedEditBtnEventObserver() {
        viewModel.isClickedEditBtnEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                showTodoEditBottomSheet(it)
            }
        )
    }

    private fun initEditTodoObserver() {
        viewModel.editTodo.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
            // Todo: 토스트 띄우기
            Timber.d("예약 후")
        }
    }

    private fun showTodoEditBottomSheet(todo: Todo) {
        TodoEditBottomSheet(viewModel, todo).show(parentFragmentManager, this.javaClass.name)
    }

    private fun initBackBtnClickListener() {
        binding.ivTodoEventEditToolBarTitle.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
