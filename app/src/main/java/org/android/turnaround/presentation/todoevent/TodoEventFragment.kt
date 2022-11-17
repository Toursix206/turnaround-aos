package org.android.turnaround.presentation.todoevent

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentTodoEventBinding
import androidx.navigation.fragment.findNavController
import org.android.turnaround.presentation.todoevent.adaprer.TodoEventAdapter
import org.android.turnaround.util.binding.BindingFragment
import org.android.turnaround.util.extension.repeatOnStarted

@AndroidEntryPoint
class TodoEventFragment : BindingFragment<FragmentTodoEventBinding>(R.layout.fragment_todo_event) {
    private val viewModel by viewModels<TodoEventViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initTodoListCollector()
        initOpenTodoEventEEventEditClickListener()
    }

    private fun showTodoStartBottomSheet() {
//        TodoStartBottomSheet().show(parentFragmentManager, this.javaClass.name)
    }

    private fun initTodoListCollector() {
        repeatOnStarted {
            viewModel.todoList.collect { todoList ->
                binding.rvTodoEvent.adapter = TodoEventAdapter(
                    context = requireContext(),
                    showBottomSheet = { _ -> showTodoStartBottomSheet() }
                ).apply {
                    submitTodoEventList(todoList)
                }
            }
        }
    }

    private fun initOpenTodoEventEEventEditClickListener() {
        binding.ivTodoEventSetting.setOnClickListener {
            findNavController().navigate(R.id.action_todoEventFragment_to_todoEventEditFragment)
        }
    }
}
