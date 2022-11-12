package org.android.turnaround.presentation.home

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.viewpager2.widget.ViewPager2
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentHomeBinding
import org.android.turnaround.domain.entity.*
import org.android.turnaround.presentation.home.adapter.TodoAdapter
import org.android.turnaround.util.binding.BindingFragment

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initHomeActivityAdapter()
    }

    private fun initHomeActivityAdapter() {
        with(binding.vpHomeTodo) {
            adapter = TodoAdapter().apply {
                val testList = listOf<Todo>(
                    NoTodo("민영"),
                    TodoWhite("침대", "졸리다", "10시까지", R.drawable.ic_home_broom),
                    TodoBlack("침대", "자야죠", R.drawable.ic_main_my),
                    TodoPurple("화장실", "청소는 실어", R.drawable.ic_home_broom)
                )
                submitHomeActivityList(testList)

                val pageMargin = resources.getDimension(R.dimen.vp_home_page_margin)
                val pagerOffset = resources.getDimension(R.dimen.vp_home_pager_offset)
                setShowSideItems(pageMargin, pagerOffset)
            }
        }
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
