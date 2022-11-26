package org.android.turnaround.presentation.todoevent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import org.android.turnaround.R
import org.android.turnaround.databinding.DialogTodoDoneBinding
import org.android.turnaround.util.dialog.initLayout

class TodoDoneDialogFragment : DialogFragment() {
    private var _binding: DialogTodoDoneBinding? = null
    private val binding get() = _binding ?: error(getString(R.string.binding_error))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogTodoDoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
        initBroomCount()
        initDoneClickListener()
    }

    private fun initBroomCount() {
        val broomCount = arguments?.getInt(BROOM_COUNT)
        binding.broom = broomCount
    }

    private fun initDoneClickListener() {
        binding.btnTodoDone.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        const val DIALOG_TODO_DONE = "todo done dialog"
        const val BROOM_COUNT = "broom count"
    }
}
