package org.android.turnaround.presentation.todo_guide.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.android.turnaround.databinding.ItemTodoGuideBinding
import org.android.turnaround.domain.entity.Guide
import org.android.turnaround.util.ItemDiffCallback

class TodoGuideAdapter : ListAdapter<Guide, TodoGuideAdapter.TodoGuideViewHolder>(todoGuideDiffUtil) {
    class TodoGuideViewHolder(
        private val binding: ItemTodoGuideBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(guide: Guide) {
            binding.imgUrl = guide.imageUrl
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoGuideViewHolder =
        TodoGuideViewHolder(
            ItemTodoGuideBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TodoGuideViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val todoGuideDiffUtil = ItemDiffCallback<Guide>(
            onItemsTheSame = { old, new -> old.step == new.step },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}
