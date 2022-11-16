package org.android.turnaround.presentation.room

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentRoomBinding
import org.android.turnaround.domain.entity.CleanLevel
import org.android.turnaround.util.binding.BindingFragment
import org.android.turnaround.util.extension.repeatOnStarted

@AndroidEntryPoint
class RoomFragment : BindingFragment<FragmentRoomBinding>(R.layout.fragment_room) {
    private val viewModel by viewModels<RoomViewModel>()
    private var windowScaleAnimator: Animator? = null
    private var bedScaleAnimator: Animator? = null
    private var deskScaleAnimator: Animator? = null
    private var windowCleanFadeOutAnimator: Animator? = null
    private var bedCleanFadeOutAnimator: Animator? = null
    private var deskCleanFadeOutAnimator: Animator? = null
    private var windowCleanFadeInAnimator: Animator? = null
    private var bedCleanFadeInAnimator: Animator? = null
    private var deskCleanFadeInAnimator: Animator? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        initRoomAssetsScaleAnimator()
        initCleanAnimator()
        initClickedAssetsCollector()
        initCleanLevelCollector()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        windowScaleAnimator = null
        bedScaleAnimator = null
        deskScaleAnimator = null
        windowCleanFadeOutAnimator = null
        bedCleanFadeOutAnimator = null
        deskCleanFadeOutAnimator = null
        windowCleanFadeInAnimator = null
        bedCleanFadeInAnimator = null
        deskCleanFadeInAnimator = null
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
        deskScaleAnimator = AnimatorInflater.loadAnimator(
            context,
            R.animator.anim_scale
        ).apply {
            setTarget(binding.ivRoomDesk)
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
                    binding.btnRoomWindowBrush.isClickable = true
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
                    initIvWindowResource()
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
                    binding.btnRoomBedBrush.isClickable = true
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
                    initIvBedResource()
                    bedCleanFadeInAnimator?.start()
                }
            })
        }

        deskCleanFadeInAnimator = AnimatorInflater.loadAnimator(
            context,
            R.animator.anim_scale_fade_in
        ).apply {
            setTarget(binding.ivRoomDesk)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    binding.btnRoomDeskBrush.isClickable = true
                }
            })
        }

        deskCleanFadeOutAnimator = AnimatorInflater.loadAnimator(
            context,
            R.animator.anim_scale_fade_out
        ).apply {
            setTarget(binding.ivRoomDesk)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    binding.btnRoomDeskBrush.isClickable = false
                }

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    initIvDeskResource()
                    deskCleanFadeInAnimator?.start()
                }
            })
        }
    }

    private fun initIvWindowResource() {
        binding.ivRoomWindow.setImageResource(
            when (viewModel.windowLevel.value) {
                CleanLevel.CLEAN -> R.drawable.ic_roomtaverse_window_1
                CleanLevel.DIRTY -> R.drawable.ic_roomtaverse_window_2
                CleanLevel.VERY_DIRTY -> R.drawable.ic_roomtaverse_window_3
            }
        )
    }

    private fun initIvBedResource() {
        binding.ivRoomBed.setImageResource(
            when (viewModel.bedLevel.value) {
                CleanLevel.CLEAN -> R.drawable.ic_roomtaverse_bed_1
                CleanLevel.DIRTY -> R.drawable.ic_roomtaverse_bed_2
                CleanLevel.VERY_DIRTY -> R.drawable.ic_roomtaverse_bed_3
            }
        )
    }

    private fun initIvDeskResource() {
        binding.ivRoomDesk.setImageResource(
            when (viewModel.deskLevel.value) {
                CleanLevel.CLEAN -> R.drawable.ic_roomtaverse_desk_1
                CleanLevel.DIRTY -> R.drawable.ic_roomtaverse_desk_2
                CleanLevel.VERY_DIRTY -> R.drawable.ic_roomtaverse_desk_3
            }
        )
    }

    private fun initClickedAssetsCollector() {
        repeatOnStarted {
            viewModel.clickedWindow.collect {
                windowScaleAnimator?.start()
            }
        }
        repeatOnStarted {
            viewModel.clickedBed.collect {
                bedScaleAnimator?.start()
            }
        }
        repeatOnStarted {
            viewModel.clickedDesk.collect {
                deskScaleAnimator?.start()
            }
        }
    }

    private fun initCleanLevelCollector() {
        repeatOnStarted {
            viewModel.windowLevel.collect {
                windowCleanFadeOutAnimator?.start()
            }
        }
        repeatOnStarted {
            viewModel.bedLevel.collect {
                bedCleanFadeOutAnimator?.start()
            }
        }
        repeatOnStarted {
            viewModel.deskLevel.collect {
                deskCleanFadeOutAnimator?.start()
            }
        }
    }
}
