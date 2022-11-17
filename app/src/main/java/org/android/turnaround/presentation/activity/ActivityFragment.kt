package org.android.turnaround.presentation.activity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentActivityBinding
import org.android.turnaround.presentation.activity.paging.ActivityPagingAdapter
import org.android.turnaround.util.binding.BindingFragment
import org.android.turnaround.util.extension.repeatOnStarted

@AndroidEntryPoint
class ActivityFragment : BindingFragment<FragmentActivityBinding>(R.layout.fragment_activity) {
    private val viewModel by viewModels<ActivityViewModel>()
    private val activityAdapter = ActivityPagingAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActivityAdapter()
        collectActivityList()
    }

    private fun initActivityAdapter() {
        binding.rvActivity.adapter = activityAdapter
    }

    private fun collectActivityList() {
        repeatOnStarted {
            viewModel.getActivities().collectLatest { activities ->
                activityAdapter.submitData(activities)
            }
        }
    }
}
