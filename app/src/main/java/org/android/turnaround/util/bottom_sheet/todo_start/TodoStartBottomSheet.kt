package org.android.turnaround.util.bottom_sheet.todo_start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.android.turnaround.R
import org.android.turnaround.databinding.BottomSheetTodoStartBinding
import org.android.turnaround.domain.entity.TodoDetail
import org.android.turnaround.util.dialog.DialogBtnClickListener
import timber.log.Timber

class TodoStartBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomSheetTodoStartBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetTodoStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.todoDetail = requireNotNull(arguments?.getParcelable<TodoDetail>(TODO_START_CONTENT))
        initBtnTodoStartClickListener()
    }

    private fun initBtnTodoStartClickListener() {
        binding.btnTodoStart.setOnClickListener {
            arguments?.getParcelable<DialogBtnClickListener>(CONFIRM_ACTION)?.onConfirmClick()
                ?: Timber.e(getString(R.string.null_point_exception_warning_dialog_argument))
        }
    }

    companion object {
        const val BOTTOM_SHEET_TODO_START = "bottomSheetTodoStart"
        const val TODO_START_CONTENT = "todoStartContent"
        const val CONFIRM_ACTION = "confirmAction"
    }
}
