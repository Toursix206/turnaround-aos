package org.android.turnaround.util

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import dagger.hilt.android.AndroidEntryPoint
import org.android.turnaround.R
import org.android.turnaround.databinding.FragmentDialogTurnaroundBinding

@AndroidEntryPoint
class TurnAroundFragmentDialog : DialogFragment() {
    private var _binding: FragmentDialogTurnaroundBinding? = null
    private val binding get() = _binding ?: error(getString(R.string.binding_error))

    lateinit var title: String
    lateinit var content: String
    lateinit var negativeText: String
    lateinit var positiveText: String
    lateinit var positiveBtnClickListener: (view: View) -> (Unit)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_turnaround, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        setDialog()
    }

    private fun initLayout() {
        requireNotNull(dialog).apply {
            requireNotNull(window).apply {
                setLayout(
                    (resources.displayMetrics.widthPixels * 1.0).toInt(),
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                setBackgroundDrawableResource(R.drawable.shape_white_rect_fill_16_with_dark_shadow)
            }
        }
    }

    private fun setDialog() {
        binding.title = title
        binding.content = content
        binding.cancelText = negativeText
        binding.confirmText = positiveText
        binding.tvDialogTaCancel.setOnClickListener { dismiss() }
        binding.tvDialogTaConfirm.setOnClickListener(positiveBtnClickListener)
        binding.tvDialogTaConfirm.setOnTouchListener { _, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                binding.tvDialogTaConfirm.performClick()
                dismiss()
            }
            false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class Builder(val context: Context) {
        private var dialog = TurnAroundFragmentDialog()

        fun setTitle(title: String): Builder {
            dialog.title = title
            return this
        }

        fun setContent(content: String): Builder {
            dialog.content = content
            return this
        }

        fun setNegativeButton(negativeText: String): Builder {
            dialog.negativeText = negativeText
            return this
        }

        fun setPositiveButton(positiveText: String, listener: (view: View) -> (Unit)): Builder {
            dialog.positiveText = positiveText
            dialog.positiveBtnClickListener = listener
            return this
        }

        fun show(manager: FragmentManager, tag: String): TurnAroundFragmentDialog {
            dialog.show(manager, tag)
            return dialog
        }
    }
}
