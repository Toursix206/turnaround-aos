package org.android.turnaround.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentActivityBinding
import org.android.turnaround.presentation.activity.paging.ActivityPagingAdapter
import org.android.turnaround.util.binding.BindingFragment
import org.android.turnaround.util.bottom_sheet.TodoReserveBottomSheet
import org.android.turnaround.util.bottom_sheet.TodoReserveContent
import org.android.turnaround.util.bottom_sheet.TodoReserveType
import org.android.turnaround.util.extension.repeatOnStarted

@AndroidEntryPoint
class ActivityFragment : BindingFragment<FragmentActivityBinding>(R.layout.fragment_activity) {
    private val viewModel by viewModels<ActivityViewModel>()
    private val activityAdapter = ActivityPagingAdapter { todoReserveContent -> showReserveBottomSheet(todoReserveContent) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        initActivityAdapter()
        initCategoryCollector()
    }

    private fun initActivityAdapter() {
        binding.rvActivity.adapter = activityAdapter
    }

    private fun showReserveBottomSheet(todoReserveContent: TodoReserveContent) {
        TodoReserveBottomSheet().apply {
            arguments = Bundle().apply {
                putSerializable(TodoReserveBottomSheet.RESERVE_TYPE, TodoReserveType.CREATE_MODE)
                putParcelable(TodoReserveBottomSheet.RESERVE_CONTENT, todoReserveContent)
            }
        }.show(parentFragmentManager, TodoReserveBottomSheet.BOTTOM_SHEET_RESERVE)
    }

    private fun initCategoryCollector() {
        repeatOnStarted {
            viewModel.category.collect {
                activityAdapter.submitData(lifecycle, PagingData.empty())
                collectActivityList()
            }
        }
    }

    private fun collectActivityList() {
        repeatOnStarted {
            viewModel.getActivities().collectLatest { activities ->
                activityAdapter.submitData(activities)
            }
        }
    }
}
