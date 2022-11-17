package org.android.turnaround.presentation.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentHomeBinding
import org.android.turnaround.domain.entity.*
import org.android.turnaround.presentation.home.adapter.TodoAdapter
import org.android.turnaround.util.binding.BindingFragment

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        initHomeObserver()
        initTodoEventEventClickListener()
    }

    private fun initHomeObserver() {
        viewModel.home.observe(viewLifecycleOwner) {
            binding.home = it
            initHomeAdapter(it.todosCnt, it.todos)
        }
    }

    private fun initHomeAdapter(todosCnt: Int, todos: List<Todo>) {
        with(binding.vpHomeTodo) {
            adapter = TodoAdapter(
                showBottomSheet = { _ -> showTodoStartBottomSheet() }
            ).apply {
                if (todosCnt > 0) submitHomeActivityList(todos)
                val pageMargin = resources.getDimension(R.dimen.vp_home_page_margin)
                val pagerOffset = resources.getDimension(R.dimen.vp_home_pager_offset)
                setShowSideItems(pageMargin, pagerOffset)
            }
        }
    }

    private fun initTodoEventEventClickListener() {
        binding.tvHomeShowMore.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_todoEventFragment)
        }
    }

    private fun showTodoStartBottomSheet() {
        TodoStartBottomSheet().show(parentFragmentManager, this.javaClass.name)
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
