package org.android.turnaround.presentation.activity.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import org.android.turnaround.databinding.ItemActivityBinding
import org.android.turnaround.domain.entity.ActivityContent
import org.android.turnaround.util.ItemDiffCallback

class ActivityPagingAdapter :
    PagingDataAdapter<ActivityContent, ActivityPagingAdapter.ActivityViewHolder>(activityDiffUtil) {
    class ActivityViewHolder(
        private val binding: ItemActivityBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(activityContent: ActivityContent) {
            binding.activity = activityContent
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder =
        ActivityViewHolder(ItemActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    companion object {
        private val activityDiffUtil =
            ItemDiffCallback<ActivityContent>(
                onItemsTheSame = { old, new -> old.activityId == new.activityId },
                onContentsTheSame = { old, new -> old == new }
            )
    }
}
