package org.android.turnaround.presentation.room

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentRoomBinding
import org.android.turnaround.domain.entity.CleanScore
import org.android.turnaround.util.binding.BindingFragment
import org.android.turnaround.util.extension.repeatOnStarted

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
        initCleanScoreCollector()
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
        ).apply { setTarget(binding.ivRoomWindow) }

        windowCleanFadeOutAnimator = AnimatorInflater.loadAnimator(
            context,
            R.animator.anim_scale_fade_out
        ).apply {
            setTarget(binding.ivRoomWindow)
            addListener(object : AnimatorListenerAdapter() {
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
        ).apply { setTarget(binding.ivRoomBed) }

        bedCleanFadeOutAnimator = AnimatorInflater.loadAnimator(
            context,
            R.animator.anim_scale_fade_out
        ).apply {
            setTarget(binding.ivRoomBed)
            addListener(object : AnimatorListenerAdapter() {
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
        ).apply { setTarget(binding.ivRoomDesk) }

        deskCleanFadeOutAnimator = AnimatorInflater.loadAnimator(
            context,
            R.animator.anim_scale_fade_out
        ).apply {
            setTarget(binding.ivRoomDesk)
            addListener(object : AnimatorListenerAdapter() {
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
            when (viewModel.windowScore.value) {
                CleanScore.CLEAN -> R.drawable.ic_roomtaverse_window_1
                CleanScore.DIRTY -> R.drawable.ic_roomtaverse_window_2
                CleanScore.VERY_DIRTY -> R.drawable.ic_roomtaverse_window_3
            }
        )
    }

    private fun initIvBedResource() {
        binding.ivRoomBed.setImageResource(
            when (viewModel.bedScore.value) {
                CleanScore.CLEAN -> R.drawable.ic_roomtaverse_bed_1
                CleanScore.DIRTY -> R.drawable.ic_roomtaverse_bed_2
                CleanScore.VERY_DIRTY -> R.drawable.ic_roomtaverse_bed_3
            }
        )
    }

    private fun initIvDeskResource() {
        binding.ivRoomDesk.setImageResource(
            when (viewModel.deskScore.value) {
                CleanScore.CLEAN -> R.drawable.ic_roomtaverse_desk_1
                CleanScore.DIRTY -> R.drawable.ic_roomtaverse_desk_2
                CleanScore.VERY_DIRTY -> R.drawable.ic_roomtaverse_desk_3
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

    private fun initCleanScoreCollector() {
        repeatOnStarted {
            viewModel.windowScore.collect {
                windowCleanFadeOutAnimator?.start()
            }
        }
        repeatOnStarted {
            viewModel.bedScore.collect {
                bedCleanFadeOutAnimator?.start()
            }
        }
        repeatOnStarted {
            viewModel.deskScore.collect {
                deskCleanFadeOutAnimator?.start()
            }
        }
    }
}
