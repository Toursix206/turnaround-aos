package org.android.turnaround.presentation.todoevent

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentTodoEventBinding
import androidx.navigation.fragment.findNavController
import org.android.turnaround.domain.entity.TodoDetail
import org.android.turnaround.presentation.home.TodoStartBottomSheet
import org.android.turnaround.presentation.todoevent.adaprer.TodoEventAdapter
import org.android.turnaround.util.EventObserver
import org.android.turnaround.util.binding.BindingFragment

@AndroidEntryPoint
class TodoEventFragment : BindingFragment<FragmentTodoEventBinding>(R.layout.fragment_todo_event) {
    private val viewModel by viewModels<TodoEventViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initTodoListObserver()
        initIsClickedBlackItemEventObserver()
        initTodoDetailObserver()
        initOpenTodoEventEventEditClickListener()
        initBackBtnClickListener()
    }

    private fun initTodoListObserver() {
        viewModel.todoList.observe(viewLifecycleOwner) {
            binding.rvTodoEvent.adapter = TodoEventAdapter(
                context = requireContext(),
                viewModel = viewModel
            ).apply {
                submitTodoEventList(it)
            }
        }
    }

    private fun initIsClickedBlackItemEventObserver() {
        viewModel.isClickedBlackItemEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                viewModel.getTodoDetail(it)
            }
        )
    }

    private fun initTodoDetailObserver() {
        viewModel.todoDetail.observe(viewLifecycleOwner) {
            showTodoStartBottomSheet(it)
        }
    }

    private fun showTodoStartBottomSheet(todoDetail: TodoDetail) {
        TodoStartBottomSheet(todoDetail).show(parentFragmentManager, this.javaClass.name)
    }

    private fun initOpenTodoEventEventEditClickListener() {
        binding.ivTodoEventSetting.setOnClickListener {
            findNavController().navigate(R.id.action_todoEventFragment_to_todoEventEditFragment)
        }
    }

    private fun initBackBtnClickListener() {
        binding.ivTodoEventBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
