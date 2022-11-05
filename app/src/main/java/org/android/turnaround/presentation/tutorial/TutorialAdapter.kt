package org.android.turnaround.presentation.tutorial

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.android.turnaround.databinding.ItemTutorialBinding
import org.android.turnaround.util.ItemDiffCallback

class TutorialAdapter : ListAdapter<Tutorial, TutorialAdapter.TutorialViewHolder>(tutorialDiffUtil) {
    class TutorialViewHolder(
        private val binding: ItemTutorialBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tutorial: Tutorial) {
            binding.tutorial = tutorial
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorialViewHolder =
        TutorialViewHolder(
            ItemTutorialBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: TutorialViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val tutorialDiffUtil = ItemDiffCallback<Tutorial>(
            onItemsTheSame = { old, new -> old.id == new.id },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}
