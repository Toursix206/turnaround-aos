package org.android.turnaround.presentation.todoeventedit

import android.os.Bundle
import android.view.View
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentTodoEventEditBinding
import org.android.turnaround.domain.entity.Todo
import org.android.turnaround.domain.entity.TodoList
import org.android.turnaround.presentation.todoeventedit.adapter.TodoEventEditAdapter
import org.android.turnaround.util.binding.BindingFragment

class TodoEventEditFragment : BindingFragment<FragmentTodoEventEditBinding>(R.layout.fragment_todo_event_edit) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        binding.rvTodoEventEdit.adapter = TodoEventEditAdapter(
            context = requireContext()
        ).apply {
            submitTodoEventEditList(todoList)
        }
    }
}
