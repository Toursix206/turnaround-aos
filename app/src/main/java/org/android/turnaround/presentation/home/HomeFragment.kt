package org.android.turnaround.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentHomeBinding
import org.android.turnaround.domain.entity.*
import org.android.turnaround.presentation.home.adapter.TodoAdapter
import org.android.turnaround.presentation.todoevent.TodoEventActivity
import org.android.turnaround.util.binding.BindingFragment

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initHomeActivityAdapter()
        initTvShowMoreClickListener()
    }

    private fun initHomeActivityAdapter() {
        with(binding.vpHomeTodo) {
            adapter = TodoAdapter(
                nickname = "민영",
                showBottomSheet = { _ -> showTodoStartBottomSheet() }
            ).apply {
                val testList = listOf<Todo>()
                submitHomeActivityList(testList)

                val pageMargin = resources.getDimension(R.dimen.vp_home_page_margin)
                val pagerOffset = resources.getDimension(R.dimen.vp_home_pager_offset)
                setShowSideItems(pageMargin, pagerOffset)
            }
        }
    }

    private fun initTvShowMoreClickListener() {
        binding.tvHomeShowMore.setOnClickListener {
            startActivity(Intent(requireActivity(), TodoEventActivity::class.java))
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
