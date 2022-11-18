package org.android.turnaround.presentation.room

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentRoomBinding
import org.android.turnaround.util.binding.BindingFragment
import org.android.turnaround.util.extension.repeatOnStarted

@AndroidEntryPoint
class RoomFragment : BindingFragment<FragmentRoomBinding>(R.layout.fragment_room) {
    private val viewModel by viewModels<RoomViewModel>()
    private var windowScaleAnimator: Animator? = null
    private var bedScaleAnimator: Animator? = null
    private var tableScaleAnimator: Animator? = null
    private var windowCleanFadeOutAnimator: Animator? = null
    private var bedCleanFadeOutAnimator: Animator? = null
    private var tableCleanFadeOutAnimator: Animator? = null
    private var windowCleanFadeInAnimator: Animator? = null
    private var bedCleanFadeInAnimator: Animator? = null
    private var tableCleanFadeInAnimator: Animator? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        viewModel.getRoom()
        initRoomAssetsScaleAnimator()
        initCleanAnimator()
        initClickedAssetsCollector()
        initCleanLevelCollector()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.resetIsSuccessGetRoomInfo()
        windowScaleAnimator = null
        bedScaleAnimator = null
        tableScaleAnimator = null
        windowCleanFadeOutAnimator = null
        bedCleanFadeOutAnimator = null
        tableCleanFadeOutAnimator = null
        windowCleanFadeInAnimator = null
        bedCleanFadeInAnimator = null
        tableCleanFadeInAnimator = null
    }

    private fun initRoomAssetsScaleAnimator() {
        windowScaleAnimator = AnimatorInflater.loadAnimator(
            context,
            R.animator.anim_scale
        ).apply {
            setTarget(binding.ivRoomWindow)
        }
        bedScaleAnimator = AnimatorInflater.loadAnimator(
            context,
            R.animator.anim_scale
        ).apply {
            setTarget(binding.ivRoomBed)
        }
        tableScaleAnimator = AnimatorInflater.loadAnimator(
            context,
            R.animator.anim_scale
        ).apply {
            setTarget(binding.ivRoomTable)
        }
    }

    private fun initCleanAnimator() {
        windowCleanFadeInAnimator = AnimatorInflater.loadAnimator(
            context,
            R.animator.anim_scale_fade_in
        ).apply {
            setTarget(binding.ivRoomWindow)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    with(binding.btnRoomWindowBrush) {
                        if (viewModel.window.value.isCleanable) isClickable = true
                        else isVisible = false
                    }
                }
            })
        }

        windowCleanFadeOutAnimator = AnimatorInflater.loadAnimator(
            context,
            R.animator.anim_scale_fade_out
        ).apply {
            setTarget(binding.ivRoomWindow)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    binding.btnRoomWindowBrush.isClickable = false
                }

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    windowCleanFadeInAnimator?.start()
                }
            })
        }

        bedCleanFadeInAnimator = AnimatorInflater.loadAnimator(
            context,
            R.animator.anim_scale_fade_in
        ).apply {
            setTarget(binding.ivRoomBed)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    with(binding.btnRoomBedBrush) {
                        if (viewModel.bed.value.isCleanable) isClickable = true
                        else isVisible = false
                    }
                }
            })
        }

        bedCleanFadeOutAnimator = AnimatorInflater.loadAnimator(
            context,
            R.animator.anim_scale_fade_out
        ).apply {
            setTarget(binding.ivRoomBed)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    binding.btnRoomBedBrush.isClickable = false
                }

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    bedCleanFadeInAnimator?.start()
                }
            })
        }

        tableCleanFadeInAnimator = AnimatorInflater.loadAnimator(
            context,
            R.animator.anim_scale_fade_in
        ).apply {
            setTarget(binding.ivRoomTable)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    with(binding.btnRoomTableBrush) {
                        if (viewModel.table.value.isCleanable) isClickable = true
                        else isVisible = false
                    }
                }
            })
        }

        tableCleanFadeOutAnimator = AnimatorInflater.loadAnimator(
            context,
            R.animator.anim_scale_fade_out
        ).apply {
            setTarget(binding.ivRoomTable)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    binding.btnRoomTableBrush.isClickable = false
                }

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    tableCleanFadeInAnimator?.start()
                }
            })
        }
    }

    private fun initClickedAssetsCollector() {
        repeatOnStarted {
            viewModel.clickedWindow.collect { isClicked ->
                if (isClicked) windowScaleAnimator?.start()
            }
        }
        repeatOnStarted {
            viewModel.clickedBed.collect { isClicked ->
                if (isClicked) bedScaleAnimator?.start()
            }
        }
        repeatOnStarted {
            viewModel.clickedTable.collect { isClicked ->
                if (isClicked) tableScaleAnimator?.start()
            }
        }
    }

    private fun initCleanLevelCollector() {
        repeatOnStarted {
            viewModel.window.collect {
                if (viewModel.isSuccessGetRoomInfo.value) windowCleanFadeOutAnimator?.start()
            }
        }
        repeatOnStarted {
            viewModel.bed.collect {
                if (viewModel.isSuccessGetRoomInfo.value) bedCleanFadeOutAnimator?.start()
            }
        }
        repeatOnStarted {
            viewModel.table.collect {
                if (viewModel.isSuccessGetRoomInfo.value) tableCleanFadeOutAnimator?.start()
            }
        }
    }
}
