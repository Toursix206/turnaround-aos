package org.android.turnaround.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.android.turnaround.databinding.ItemTodoBlackBinding
import org.android.turnaround.databinding.ItemTodoPurpleBinding
import org.android.turnaround.databinding.ItemTodoWhiteBinding
import org.android.turnaround.domain.entity.*

class TodoAdapter(
    private val showBottomSheet: (Unit) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val todoList = mutableListOf<Todo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_TODO_BLACK -> TodoBlackViewHolder(ItemTodoBlackBinding.inflate(inflater, parent, false))
            VIEW_TYPE_TODO_PURPLE -> TodoPurpleViewHolder(ItemTodoPurpleBinding.inflate(inflater, parent, false))
            else -> TodoWhiteViewHolder(ItemTodoWhiteBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TodoWhiteViewHolder -> {
                val item = todoList[position]
                holder.bind(item)
            }
            is TodoBlackViewHolder -> {
                val item = todoList[position]
                holder.bind(item)
            }
            is TodoPurpleViewHolder -> {
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

    fun submitHomeActivityList(items: List<Todo>) {
        todoList.addAll(items)
        notifyItemRangeInserted(items.size, items.size)
    }

    inner class TodoWhiteViewHolder(private val binding: ItemTodoWhiteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            binding.todo = todo
            binding.executePendingBindings()
        }
    }

    inner class TodoBlackViewHolder(private val binding: ItemTodoBlackBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            binding.todo = todo
            binding.viewTodoBlack.setOnClickListener { showBottomSheet(Unit) }
            binding.executePendingBindings()
        }
    }

    inner class TodoPurpleViewHolder(private val binding: ItemTodoPurpleBinding) : RecyclerView.ViewHolder(binding.root) {
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
