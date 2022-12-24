package org.android.turnaround.presentation.activity.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import org.android.turnaround.databinding.ItemActivityBinding
import org.android.turnaround.domain.entity.ActivityContent
import org.android.turnaround.domain.entity.PushStatusType
import org.android.turnaround.util.ItemDiffCallback
import org.android.turnaround.util.bottom_sheet.todo_reserve.TodoReserveContent

class ActivityPagingAdapter(
    private val showReserveBottomSheet: (TodoReserveContent) -> Unit
) : PagingDataAdapter<ActivityContent, ActivityPagingAdapter.ActivityViewHolder>(activityDiffUtil) {
    class ActivityViewHolder(
        private val binding: ItemActivityBinding,
        private val showReserveBottomSheet: (TodoReserveContent) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            initItemClickListener()
        }

        fun bind(activityContent: ActivityContent) {
            binding.activity = activityContent
            binding.executePendingBindings()
        }

        private fun initItemClickListener() {
            binding.layoutActivityItem.setOnClickListener {
                showReserveBottomSheet(
                    TodoReserveContent(
                        id = requireNotNull(binding.activity).activityId,
                        duration = requireNotNull(binding.activity).duration,
                        pushStatus = PushStatusType.OFF
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder =
        ActivityViewHolder(ItemActivityBinding.inflate(LayoutInflater.from(parent.context), parent, false), showReserveBottomSheet)

    companion object {
        private val activityDiffUtil =
            ItemDiffCallback<ActivityContent>(
                onItemsTheSame = { old, new -> old.activityId == new.activityId },
                onContentsTheSame = { old, new -> old == new }
            )
    }
}
