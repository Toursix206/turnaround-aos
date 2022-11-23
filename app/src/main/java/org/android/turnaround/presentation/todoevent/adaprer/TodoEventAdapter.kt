package org.android.turnaround.presentation.todoevent.adaprer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.android.turnaround.R
import org.android.turnaround.databinding.*
import org.android.turnaround.domain.entity.*
import org.android.turnaround.presentation.todoevent.TodoEventViewModel

class TodoEventAdapter(
    private val context: Context,
    private val viewModel: TodoEventViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val todoEventList = mutableListOf<TodoEvent>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_TODO_HEADER -> TodoEvenHeaderViewHolder(ItemTodoEventHeaderBinding.inflate(inflater, parent, false))
            VIEW_TYPE_TODO_BLACK -> TodoEventBlackViewHolder(ItemTodoEventBlackBinding.inflate(inflater, parent, false))
            VIEW_TYPE_TODO_PURPLE -> TodoEventPurpleViewHolder(ItemTodoEventPurpleBinding.inflate(inflater, parent, false))
            else -> TodoEventWhiteViewHolder(ItemTodoEventWhiteBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TodoEvenHeaderViewHolder -> {
                val item = todoEventList[position] as TodoHeader
                holder.bind(item)
            }
            is TodoEventWhiteViewHolder -> {
                val item = todoEventList[position] as Todo
                holder.bind(item)
            }
            is TodoEventBlackViewHolder -> {
                val item = todoEventList[position] as Todo
                holder.bind(item)
            }
            is TodoEventPurpleViewHolder -> {
                val item = todoEventList[position] as Todo
                holder.bind(item)
            }
        }
    }

    override fun getItemCount(): Int = todoEventList.size

    override fun getItemViewType(position: Int): Int {
        return when (todoEventList[position]) {
            is TodoHeader -> VIEW_TYPE_TODO_HEADER
            is Todo -> {
                return when ((todoEventList[position] as Todo).todoStatus) {
                    TodoType.WHITE -> VIEW_TYPE_TODO_WHITE
                    TodoType.BLACK -> VIEW_TYPE_TODO_BLACK
                    TodoType.PURPLE -> VIEW_TYPE_TODO_PURPLE
                }
            }
        }
    }

    fun submitTodoEventList(todoList: TodoList) {
        val data = mutableListOf<TodoEvent>()
        // 완료된 활동
        if (todoList.successTodosCnt > 0) {
            val header = TodoHeader(context.getString(R.string.todo_event_todo_success), todoList.successTodosCnt)
            data.add(header)
            data.addAll(todoList.successTodos)
        }
        // 오늘의 활동
        if (todoList.todayTodosCnt > 0) {
            val header = TodoHeader(context.getString(R.string.todo_event_todo_today), todoList.todayTodosCnt)
            data.add(header)
            data.addAll(todoList.todayTodos)
        }
        // 이번주 활동
        if (todoList.thisWeekTodosCnt > 0) {
            val header = TodoHeader(context.getString(R.string.todo_event_todo_this_week), todoList.thisWeekTodosCnt)
            data.add(header)
            data.addAll(todoList.thisWeekTodos)
        }
        // 다음 활동
        if (todoList.nextTodosCnt > 0) {
            val header = TodoHeader(context.getString(R.string.todo_event_todo_next), todoList.nextTodosCnt)
            data.add(header)
            data.addAll(todoList.nextTodos)
        }
        todoEventList.addAll(data)
        notifyItemRangeInserted(todoEventList.size, data.size)
    }

    inner class TodoEvenHeaderViewHolder(private val binding: ItemTodoEventHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(header: TodoHeader) {
            binding.header = header
            binding.executePendingBindings()
        }
    }

    inner class TodoEventWhiteViewHolder(private val binding: ItemTodoEventWhiteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            binding.todo = todo
            binding.vm = viewModel
            binding.executePendingBindings()
        }
    }

    inner class TodoEventBlackViewHolder(private val binding: ItemTodoEventBlackBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            binding.todo = todo
            binding.vm = viewModel
            binding.executePendingBindings()
        }
    }

    inner class TodoEventPurpleViewHolder(private val binding: ItemTodoEventPurpleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            binding.todo = todo
            binding.executePendingBindings()
        }
    }

    companion object {
        const val VIEW_TYPE_TODO_HEADER = 0
        const val VIEW_TYPE_TODO_WHITE = 1
        const val VIEW_TYPE_TODO_BLACK = 2
        const val VIEW_TYPE_TODO_PURPLE = 3
    }
}
