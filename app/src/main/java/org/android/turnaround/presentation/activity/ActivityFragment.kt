package org.android.turnaround.presentation.activity

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentActivityBinding
import org.android.turnaround.presentation.activity.ActivityViewModel.Companion.ERROR_DUPLICATE_RESERVE
import org.android.turnaround.presentation.activity.ActivityViewModel.Companion.ERROR_INVALID_RESERVE_DATE
import org.android.turnaround.presentation.activity.paging.ActivityPagingAdapter
import org.android.turnaround.util.ToastMessageUtil
import org.android.turnaround.util.UiEvent
import org.android.turnaround.util.binding.BindingFragment
import org.android.turnaround.util.bottom_sheet.todo_reserve.TodoReserveBottomSheet
import org.android.turnaround.util.bottom_sheet.todo_reserve.TodoReserveBtnClickListener
import org.android.turnaround.util.bottom_sheet.todo_reserve.TodoReserveContent
import org.android.turnaround.util.bottom_sheet.todo_reserve.TodoReserveType
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
        initReserveTodoUiEventCollector()
    }

    private fun initActivityAdapter() {
        binding.rvActivity.adapter = activityAdapter
    }

    private fun showReserveBottomSheet(todoReserveContent: TodoReserveContent) {
        TodoReserveBottomSheet().apply {
            arguments = Bundle().apply {
                putSerializable(TodoReserveBottomSheet.RESERVE_TYPE, TodoReserveType.CREATE_MODE)
                putParcelable(TodoReserveBottomSheet.RESERVE_CONTENT, todoReserveContent)
                putParcelable(
                    TodoReserveBottomSheet.CONFIRM_ACTION,
                    TodoReserveBtnClickListener(
                        clickAction = { pushStatus, startAt ->
                            viewModel.postReserveTodo(
                                activityId = todoReserveContent.id,
                                pushStatus = pushStatus,
                                startAt = startAt
                            )
                        }
                    )
                )
            }
        }.show(parentFragmentManager, TodoReserveBottomSheet.BOTTOM_SHEET_RESERVE)
    }

    private fun collectActivityList() {
        repeatOnStarted {
            viewModel.getActivities().collectLatest { activities ->
                activityAdapter.submitData(activities)
            }
        }
    }

    private fun initCategoryCollector() {
        repeatOnStarted {
            viewModel.category.collect {
                activityAdapter.submitData(lifecycle, PagingData.empty())
                collectActivityList()
            }
        }
    }

    private fun initReserveTodoUiEventCollector() {
        repeatOnStarted {
            viewModel.reserveTodoUiEvent.collect { uiEvent ->
                when (uiEvent) {
                    UiEvent.SUCCESS -> {
                        ToastMessageUtil.showPurpleToast(
                            requireContext(),
                            getString(R.string.todo_reserve_toast_msg_success),
                            false,
                            Gravity.TOP
                        )
                    }
                    UiEvent.ERROR -> {
                        showReserveErrorToast()
                    }
                    UiEvent.LOADING -> {}
                }
            }
        }
    }

    private fun showReserveErrorToast() {
        when (viewModel.reserveErrorCode) {
            ERROR_DUPLICATE_RESERVE -> ToastMessageUtil.showPurpleToast(
                requireContext(), getString(R.string.todo_reserve_toast_msg_duplicate), true, Gravity.TOP
            )
            ERROR_INVALID_RESERVE_DATE -> ToastMessageUtil.showPurpleToast(
                requireContext(), viewModel.reserveErrorMessage, true, Gravity.TOP
            )
        }
    }
}
