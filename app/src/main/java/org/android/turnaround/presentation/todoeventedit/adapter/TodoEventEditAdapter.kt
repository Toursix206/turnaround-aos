package org.android.turnaround.presentation.todoeventedit.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.android.turnaround.R
import org.android.turnaround.databinding.*
import org.android.turnaround.domain.entity.*
import org.android.turnaround.presentation.todoeventedit.TodoEventEditViewModel

class TodoEventEditAdapter(
    private val context: Context,
    private val viewModel: TodoEventEditViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val todoEventList = mutableListOf<TodoEvent>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_TODO_EDIT_HEADER -> TodoEvenHeaderViewHolder(ItemTodoEventHeaderBinding.inflate(inflater, parent, false))
            VIEW_TYPE_TODO_EDIT_BLACK -> TodoEventBlackEditViewHolder(ItemTodoEventBlackEditBinding.inflate(inflater, parent, false))
            else -> TodoEventWhiteEditViewHolder(ItemTodoEventWhiteEditBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TodoEvenHeaderViewHolder -> {
                val item = todoEventList[position] as TodoHeader
                holder.bind(item)
            }
            is TodoEventWhiteEditViewHolder -> {
                val item = todoEventList[position] as Todo
                holder.bind(item)
            }
            is TodoEventBlackEditViewHolder -> {
                val item = todoEventList[position] as Todo
                holder.bind(item)
            }
        }
    }

    override fun getItemCount(): Int = todoEventList.size

    private fun getItemPosition(todoId: Int): Int {
        todoEventList.forEachIndexed { index, todoEvent ->
            if (todoEvent is Todo && todoEvent.todoId == todoId) return index
        }
        return -1
    }

    fun deleteTodoItem(deletedTodoId: Int) {
        val deletedTodoPosition = getItemPosition(deletedTodoId)
        if (deletedTodoPosition != -1) {
            todoEventList.removeAt(deletedTodoPosition)
            notifyItemRemoved(deletedTodoPosition)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (todoEventList[position]) {
            is TodoHeader -> VIEW_TYPE_TODO_EDIT_HEADER
            is Todo -> {
                return when ((todoEventList[position] as Todo).todoStatus) {
                    TodoType.WHITE -> VIEW_TYPE_TODO_EDIT_WHITE
                    TodoType.BLACK -> VIEW_TYPE_TODO_EDIT_BLACK
                    else -> VIEW_TYPE_TODO_EDIT_WHITE
                }
            }
        }
    }

    fun submitTodoEventEditList(todoList: TodoList) {
        val data = mutableListOf<TodoEvent>()
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

    inner class TodoEventWhiteEditViewHolder(private val binding: ItemTodoEventWhiteEditBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            binding.todo = todo
            binding.vm = viewModel
            binding.executePendingBindings()
        }
    }

    inner class TodoEventBlackEditViewHolder(private val binding: ItemTodoEventBlackEditBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            binding.todo = todo
            binding.vm = viewModel
            binding.executePendingBindings()
        }
    }

    companion object {
        const val VIEW_TYPE_TODO_EDIT_HEADER = 0
        const val VIEW_TYPE_TODO_EDIT_WHITE = 1
        const val VIEW_TYPE_TODO_EDIT_BLACK = 2
    }
}
