package org.android.turnaround.presentation.todo_edit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.android.turnaround.databinding.ItemTodoEditBlackBinding
import org.android.turnaround.databinding.ItemTodoHeaderBinding
import org.android.turnaround.databinding.ItemTodoEditWhiteBinding
import org.android.turnaround.domain.entity.*
import org.android.turnaround.presentation.todo_edit.TodoEditViewModel
import org.android.turnaround.util.ItemDiffCallback

class TodoEditAdapter(
    private val viewModel: TodoEditViewModel
) : ListAdapter<TodoEvent, RecyclerView.ViewHolder>(todoDiffUtil) {

    inner class TodoEvenHeaderViewHolder(private val binding: ItemTodoHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(header: TodoHeader) {
            binding.header = header
            binding.executePendingBindings()
        }
    }

    inner class TodoEventWhiteEditViewHolder(private val binding: ItemTodoEditWhiteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            binding.todo = todo
            binding.vm = viewModel
            binding.executePendingBindings()
        }
    }

    inner class TodoEventBlackEditViewHolder(private val binding: ItemTodoEditBlackBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            binding.todo = todo
            binding.vm = viewModel
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_TODO_EDIT_HEADER -> TodoEvenHeaderViewHolder(ItemTodoHeaderBinding.inflate(inflater, parent, false))
            VIEW_TYPE_TODO_EDIT_BLACK -> TodoEventBlackEditViewHolder(ItemTodoEditBlackBinding.inflate(inflater, parent, false))
            else -> TodoEventWhiteEditViewHolder(ItemTodoEditWhiteBinding.inflate(inflater, parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is TodoHeader -> VIEW_TYPE_TODO_EDIT_HEADER
            is Todo -> {
                return when ((currentList[position] as Todo).todoStatus) {
                    TodoType.WHITE -> VIEW_TYPE_TODO_EDIT_WHITE
                    TodoType.BLACK -> VIEW_TYPE_TODO_EDIT_BLACK
                    else -> VIEW_TYPE_TODO_EDIT_WHITE
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TodoEvenHeaderViewHolder -> {
                val item = currentList[position] as TodoHeader
                holder.bind(item)
            }
            is TodoEventWhiteEditViewHolder -> {
                val item = currentList[position] as Todo
                holder.bind(item)
            }
            is TodoEventBlackEditViewHolder -> {
                val item = currentList[position] as Todo
                holder.bind(item)
            }
        }
    }

    companion object {
        const val VIEW_TYPE_TODO_EDIT_HEADER = 0
        const val VIEW_TYPE_TODO_EDIT_WHITE = 1
        const val VIEW_TYPE_TODO_EDIT_BLACK = 2

        val todoDiffUtil = ItemDiffCallback<TodoEvent>(
            onItemsTheSame = { old, new ->
                if (old is TodoHeader && new is TodoHeader) old.title == new.title
                else (old as Todo).todoId == (new as Todo).todoId
            },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}
