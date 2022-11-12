package org.android.turnaround.presentation.todoevent.adaprer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.android.turnaround.databinding.*
import org.android.turnaround.domain.entity.*

class TodoEventAdapter(
    private val showBottomSheet: (Unit) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val todoList = mutableListOf<Todo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_TODO_BLACK -> TodoEventBlackViewHolder(ItemTodoEventBlackBinding.inflate(inflater, parent, false))
            VIEW_TYPE_TODO_PURPLE -> TodoEventPurpleViewHolder(ItemTodoEventPurpleBinding.inflate(inflater, parent, false))
            else -> TodoEventWhiteViewHolder(ItemTodoEventWhiteBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TodoEventWhiteViewHolder -> {
                val item = todoList[position]
                holder.bind(item)
            }
            is TodoEventBlackViewHolder -> {
                val item = todoList[position]
                holder.bind(item)
            }
            is TodoEventPurpleViewHolder -> {
                val item = todoList[position]
                holder.bind(item)
            }
        }
    }

    override fun getItemCount(): Int = todoList.size

    override fun getItemViewType(position: Int): Int {
        return when (todoList[position].todoStatus) {
            TodoType.WHITE.type -> VIEW_TYPE_TODO_WHITE
            TodoType.BLACK.type -> VIEW_TYPE_TODO_BLACK
            TodoType.PURPLE.type -> VIEW_TYPE_TODO_PURPLE
            else -> VIEW_TYPE_TODO_WHITE
        }
    }

    fun submitTodoEventList(items: List<Todo>) {
        todoList.addAll(items)
        notifyItemRangeInserted(items.size, items.size)
    }

    inner class TodoEventWhiteViewHolder(private val binding: ItemTodoEventWhiteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            binding.todo = todo
            binding.executePendingBindings()
        }
    }

    inner class TodoEventBlackViewHolder(private val binding: ItemTodoEventBlackBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            binding.todo = todo
            binding.viewTodoBlack.setOnClickListener { showBottomSheet(Unit) }
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
        const val VIEW_TYPE_TODO_WHITE = 1
        const val VIEW_TYPE_TODO_BLACK = 2
        const val VIEW_TYPE_TODO_PURPLE = 3
    }
}
