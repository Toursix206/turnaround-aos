package org.android.turnaround.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentHomeBinding
import org.android.turnaround.domain.entity.HomeTodo
import org.android.turnaround.domain.entity.TodoDetail
import org.android.turnaround.presentation.home.adapter.TodoAdapter
import org.android.turnaround.presentation.my_todo.MyTodoActivity
import org.android.turnaround.presentation.todo_guide.TodoGuideActivity
import org.android.turnaround.presentation.todo_guide.TodoGuideActivity.Companion.TODO_GUIDE_TODO_ID
import org.android.turnaround.util.EventObserver
import org.android.turnaround.util.ToastMessageUtil
import org.android.turnaround.util.UiEvent
import org.android.turnaround.util.binding.BindingFragment
import org.android.turnaround.util.bottom_sheet.todo_start.TodoStartBottomSheet
import org.android.turnaround.util.dialog.DialogBtnClickListener
import org.android.turnaround.util.extension.repeatOnStarted

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel by viewModels<HomeViewModel>()
    private var todoStartBottomSheet = TodoStartBottomSheet()

    private val myTodoResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            refresh()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        viewModel.getHomeInfo()
        initHomeObserver()
        initIsClickedBlackItemEventObserver()
        initTodoDetailObserver()
        initStartTodoAbleEventCollector()
        initTodoEventEventClickListener()
    }

    private fun initHomeObserver() {
        viewModel.home.observe(viewLifecycleOwner) {
            initHomeAdapter(it.todosCnt, it.todos)
        }
    }

    private fun initHomeAdapter(todosCnt: Int, todos: List<HomeTodo>) {
        with(binding.vpHomeTodo) {
            adapter = TodoAdapter(viewModel).apply {
                if (todosCnt > 0) submitTodoList(todos)
                val pageMargin = resources.getDimension(R.dimen.vp_home_page_margin)
                val pagerOffset = resources.getDimension(R.dimen.vp_home_pager_offset)
                setShowSideItems(pageMargin, pagerOffset)
            }
        }
    }

    private fun initIsClickedBlackItemEventObserver() {
        viewModel.isClickedBlackItemEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                viewModel.getTodoDetail(it)
            }
        )
    }

    private fun initTodoDetailObserver() {
        viewModel.todoDetail.observe(
            viewLifecycleOwner,
            EventObserver {
                showTodoStartBottomSheet(it)
            }
        )
    }

    private fun initStartTodoAbleEventCollector() {
        repeatOnStarted {
            viewModel.todoStartAbleEvent.collect { uiEvent ->
                when (uiEvent) {
                    UiEvent.SUCCESS -> {
                        todoStartBottomSheet.dismiss()
                        startActivity(
                            Intent(requireContext(), TodoGuideActivity::class.java).apply {
                                putExtra(TODO_GUIDE_TODO_ID, requireNotNull(viewModel.todoDetail.value).peekContent().todoId)
                            }
                        )
                    }
                    UiEvent.ERROR -> {
                        ToastMessageUtil.showPurpleToast(
                            requireContext(),
                            getString(R.string.todo_reserve_toast_msg_duplicate),
                            true,
                            Gravity.TOP
                        )
                    }
                    UiEvent.LOADING -> {}
                }
            }
        }
    }

    private fun initTodoEventEventClickListener() {
        binding.tvHomeShowMore.setOnClickListener {
            myTodoResultLauncher.launch(Intent(requireActivity(), MyTodoActivity::class.java))
        }
    }

    private fun showTodoStartBottomSheet(todoDetail: TodoDetail) {
        todoStartBottomSheet.apply {
            arguments = Bundle().apply {
                putParcelable(TodoStartBottomSheet.TODO_START_CONTENT, todoDetail)
                putParcelable(
                    TodoStartBottomSheet.CONFIRM_ACTION,
                    DialogBtnClickListener(id = todoDetail.todoId, clickActionWithId = { id -> viewModel.getTodoStartAble(id) })
                )
            }
        }.show(parentFragmentManager, TodoStartBottomSheet.BOTTOM_SHEET_TODO_START)
    }

    private fun refresh() {
        viewModel.getHomeInfo()
    }

    private fun ViewPager2.setShowSideItems(pageMarginPx: Float, offsetPx: Float) {
        offscreenPageLimit = 3

        setPageTransformer { page, position ->
            val offset = position * -(2 * offsetPx + pageMarginPx)
            if (this.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
                if (ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                    page.translationX = -offset
                } else {
                    page.translationX = offset
                }
            } else {
                page.translationY = offset
            }
        }
    }
}
