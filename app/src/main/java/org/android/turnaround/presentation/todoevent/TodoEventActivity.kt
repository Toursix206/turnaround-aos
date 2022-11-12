package org.android.turnaround.presentation.todoevent

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.ActivityTodoEventBinding
import org.android.turnaround.domain.entity.Todo
import org.android.turnaround.domain.entity.TodoList
import org.android.turnaround.presentation.home.TodoStartBottomSheet
import org.android.turnaround.presentation.todoevent.adaprer.TodoEventAdapter
import org.android.turnaround.util.binding.BindingActivity
import org.android.turnaround.util.extension.repeatOnStarted
import timber.log.Timber

@AndroidEntryPoint
class TodoEventActivity : BindingActivity<ActivityTodoEventBinding>(R.layout.activity_todo_event) {
    private val viewModel by viewModels<TodoEventViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        binding.todoList = TodoList(
            listOf(todoWhite, todoWhite), 2,
            listOf(todoPurple, ), 1,
            listOf(todoWhite, todoWhite), 2,
            listOf(todoBlack, ), 1,
        )
        initTodoSuccessAdapter(listOf(todoPurple, ))
        initTodoTodayAdapter(listOf(todoBlack))
        initTodoThisWeekAdapter(listOf(todoWhite, todoWhite))
        initTodoNextAdapter(listOf(todoWhite, todoWhite))
    }

    private fun initTodoSuccessAdapter(todoList: List<Todo>) {
        binding.rvTodoEventSuccess.adapter = TodoEventAdapter(
            showBottomSheet = { _ -> showTodoStartBottomSheet() }
        ).apply {
            submitTodoEventList(todoList)
        }
    }

    private fun initTodoTodayAdapter(todoList: List<Todo>) {
        binding.rvTodoEventToday.adapter = TodoEventAdapter(
            showBottomSheet = { _ -> showTodoStartBottomSheet() }
        ).apply {
            submitTodoEventList(todoList)
        }
    }

    private fun initTodoThisWeekAdapter(todoList: List<Todo>) {
        binding.rvTodoEventThisWeek.adapter = TodoEventAdapter(
            showBottomSheet = { _ -> showTodoStartBottomSheet() }
        ).apply {
            submitTodoEventList(todoList)
        }
    }

    private fun initTodoNextAdapter(todoList: List<Todo>) {
        binding.rvTodoEventNext.adapter = TodoEventAdapter(
            showBottomSheet = { _ -> showTodoStartBottomSheet() }
        ).apply {
            submitTodoEventList(todoList )
        }
    }

    private fun showTodoStartBottomSheet() {
        TodoStartBottomSheet().show(supportFragmentManager, this.javaClass.name)
    }

    private fun initTodoListCollector() {
        repeatOnStarted {
            viewModel.todoList.collect { todoList ->
                Timber.d("mmm $todoList")
            }
        }
    }
}
