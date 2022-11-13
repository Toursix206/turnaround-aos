package org.android.turnaround.presentation.todoevent

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentTodoEventBinding
import org.android.turnaround.domain.entity.Todo
import org.android.turnaround.domain.entity.TodoList
import org.android.turnaround.presentation.home.TodoStartBottomSheet
import org.android.turnaround.presentation.todoevent.adaprer.TodoEventAdapter
import org.android.turnaround.util.binding.BindingFragment
import org.android.turnaround.util.extension.repeatOnStarted
import timber.log.Timber

@AndroidEntryPoint
class TodoEventFragment : BindingFragment<FragmentTodoEventBinding>(R.layout.fragment_todo_event) {
    private val viewModel by viewModels<TodoEventViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTodoListCollector()

        val todoWhite = Todo(
            activityCategory = "why?", "팡이팡이 곰팡이", "까지",
            1, "WHITE", categoryName = "화장실1", R.drawable.img_todo_restroom
        )
        val todoBlack = Todo(
            activityCategory = "why?", "팡이팡이 곰팡이", "까지",
            1, "BLACK", categoryName = "화장실1", R.drawable.img_todo_restroom
        )
        val todoPurple = Todo(
            activityCategory = "why?", "팡이팡이 곰팡이", "까지",
            1, "PURPLE", categoryName = "화장실1", R.drawable.img_todo_restroom
        )

        val list = TodoList(
            listOf(todoWhite, todoWhite), 2,
            listOf(todoPurple, todoPurple, todoPurple), 3,
            listOf(todoWhite, todoWhite), 2,
            listOf(todoBlack), 1,
        )
        initTodoEventAdapter(list)
    }

    private fun initTodoEventAdapter(todoList: TodoList) {
        binding.rvTodoEvent.adapter = TodoEventAdapter(
            context = requireContext(),
            showBottomSheet = { _ -> showTodoStartBottomSheet() }
        ).apply {
            submitTodoEventList(todoList)
        }
    }

    private fun showTodoStartBottomSheet() {
        TodoStartBottomSheet().show(parentFragmentManager, this.javaClass.name)
    }

    private fun initTodoListCollector() {
        repeatOnStarted {
            viewModel.todoList.collect { todoList ->
                Timber.d("mmm $todoList")
            }
        }
    }
}
