package org.android.turnaround.util.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import org.android.turnaround.R
import org.android.turnaround.databinding.DialogWarningBinding
import timber.log.Timber

class WarningDialogFragment : DialogFragment() {
    private var _binding: DialogWarningBinding? = null
    private val binding get() = _binding ?: error(getString(R.string.binding_error))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogWarningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        initWarningDialogContent()
        initCancelClickListener()
        initConfirmClickListener()
    }

    private fun initWarningDialogContent() {
        val warningType = arguments?.get(WARNING_TYPE)
            ?: Timber.e(getString(R.string.null_point_exception_warning_dialog_argument))
        with(binding) {
            warning = when (warningType as WarningType) {
                WarningType.WARNING_DELETE_ACTIVITY -> WarningDialogContent().getWarningDeleteActivity(requireContext())
                WarningType.WARNING_CANCEL_ACTIVITY -> WarningDialogContent().getWarningCancelActivity(requireContext())
                WarningType.WARNING_TAKE_PHOTO_AGAIN -> WarningDialogContent().getWarningTakePhotoAgain(requireContext())
                WarningType.WARNING_SKIP_REVIEW -> WarningDialogContent().getWarningSkipReview(requireContext())
                WarningType.WARNING_CANCEL_REVIEW -> WarningDialogContent().getWarningCancelReview(requireContext())
                WarningType.WARNING_WITHDRAW -> WarningDialogContent().getWarningWithdraw(requireContext())
            }
        }
    }

    private fun initCancelClickListener() {
        binding.btnWarningCancel.setOnClickListener {
            val warningType = arguments?.get(WARNING_TYPE)
                ?: Timber.e(getString(R.string.null_point_exception_warning_dialog_argument))
            if (warningType as WarningType == WarningType.WARNING_WITHDRAW) {
                arguments?.getParcelable<ConfirmClickListener>(CONFIRM_ACTION)?.onConfirmClick()
                    ?: Timber.e(getString(R.string.null_point_exception_warning_dialog_argument))
            }
            dismiss()
        }
    }

    private fun initConfirmClickListener() {
        binding.btnWarningConfirm.setOnClickListener {
            val warningType = arguments?.get(WARNING_TYPE)
                ?: Timber.e(getString(R.string.null_point_exception_warning_dialog_argument))
            if (warningType as WarningType != WarningType.WARNING_WITHDRAW) {
                arguments?.getParcelable<ConfirmClickListener>(CONFIRM_ACTION)?.onConfirmClick()
                    ?: Timber.e(getString(R.string.null_point_exception_warning_dialog_argument))
            }
            dismiss()
        }
    }

    companion object {
        const val DIALOG_WARNING = "warningDialog"
        const val WARNING_TYPE = "warningType"
        const val CONFIRM_ACTION = "confirmAction"
    }
}