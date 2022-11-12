package org.android.turnaround.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.android.turnaround.R
import org.android.turnaround.databinding.BottomSheetTodoStartBinding

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
        initBtnTodoStartClickListener()
    }

    private fun initBtnTodoStartClickListener() {
        binding.btnTodoStart.setOnClickListener {
            dismiss()
        }
    }
}
