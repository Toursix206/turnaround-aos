package org.android.turnaround.presentation.room

import android.animation.Animator
import android.animation.AnimatorInflater
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentRoomBinding
import org.android.turnaround.util.binding.BindingFragment
import org.android.turnaround.util.extension.repeatOnStarted

class RoomFragment : BindingFragment<FragmentRoomBinding>(R.layout.fragment_room) {
    private val viewModel by viewModels<RoomViewModel>()
    private var windowScaleAnimator: Animator? = null
    private var bedScaleAnimator: Animator? = null
    private var deskScaleAnimator: Animator? = null
    private var windowCleanAnimator: Animator? = null
    private var bedCleanAnimator: Animator? = null
    private var deskCleanAnimator: Animator? = null

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
        windowCleanAnimator = null
        bedCleanAnimator = null
        deskCleanAnimator = null
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
        windowCleanAnimator = AnimatorInflater.loadAnimator(
            context,
            R.animator.anim_scale_fade_out
        ).apply {
            setTarget(binding.ivRoomWindow)
        }
        bedCleanAnimator = AnimatorInflater.loadAnimator(
            context,
            R.animator.anim_scale_fade_out
        ).apply {
            setTarget(binding.ivRoomBed)
        }
        deskCleanAnimator = AnimatorInflater.loadAnimator(
            context,
            R.animator.anim_scale_fade_out
        ).apply {
            setTarget(binding.ivRoomDesk)
        }
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
                windowCleanAnimator?.start()
            }
        }
        repeatOnStarted {
            viewModel.bedScore.collect {
                bedCleanAnimator?.start()
            }
        }
        repeatOnStarted {
            viewModel.deskScore.collect {
                deskCleanAnimator?.start()
            }
        }
    }
}
