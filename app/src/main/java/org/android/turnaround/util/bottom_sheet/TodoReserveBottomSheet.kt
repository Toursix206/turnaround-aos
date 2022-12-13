package org.android.turnaround.util.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.android.turnaround.R
import org.android.turnaround.data.remote.entity.request.TodoEditRequest
import org.android.turnaround.databinding.BottomSheetTodoEditBinding
import org.android.turnaround.presentation.todoeventedit.TodoEventEditViewModel
import org.android.turnaround.util.dialog.DialogBtnClickListener
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class TodoReserveBottomSheet : BottomSheetDialogFragment() {
    val binding get() = _binding ?: error(getString(R.string.binding_error))
    private var _binding: BottomSheetTodoEditBinding? = null
    private val viewModel by activityViewModels<TodoEventEditViewModel>()
    private val reserveType
        get() = arguments?.get(RESERVE_TYPE)
            ?: Timber.e(getString(R.string.null_point_exception_warning_dialog_argument))
    private val todoReserveContent get() = arguments?.getParcelable<TodoReserveContent>(RESERVE_CONTENT)
    private val dateList = mutableListOf<String>()
    private val minList = mutableListOf<String>()
    private val ampmeList = mutableListOf<String>("PM", "AM")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetTodoEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        binding.reserveType = reserveType as TodoReserveType
        binding.todoReserveContent = requireNotNull(todoReserveContent)
        binding.switchBtnTodoEdit.isChecked = requireNotNull(todoReserveContent).pushStatus.checked
        initDatePicker()
        initHourPicker()
        initMinutePicker()
        initAmPmPicker()
        initConfirmClickListener()
    }

    private fun initDatePicker() {
        val today = Calendar.getInstance()
        val sdf = SimpleDateFormat("MM / dd")
        for (i in 0..13) {
            val millis = today.timeInMillis
            val now = Date(millis)
            val date = sdf.format(now)
            dateList.add(date)
            today.add(Calendar.DATE, 1)
        }
        binding.pickerTodoEditDate.let {
            it.minValue = 0
            it.displayedValues = dateList.toTypedArray()
            it.maxValue = 13
            it.wrapSelectorWheel = false
        }
    }

    private fun initHourPicker() {
        binding.pickerTodoEditHour.let {
            it.minValue = 1
            it.maxValue = 12
            it.wrapSelectorWheel = true
        }
    }

    private fun initMinutePicker() {
        for (i in 0..59) {
            if (i % 5 == 0) minList.add(if (i < 10) "0$i" else i.toString())
        }
        binding.pickerTodoEditMinute.let {
            it.minValue = 0
            it.displayedValues = minList.toTypedArray()
            it.maxValue = minList.size - 1
            it.wrapSelectorWheel = true
        }
    }

    private fun initAmPmPicker() {
        binding.pickerTodoEditAmpm.let {
            it.minValue = 0
            it.displayedValues = ampmeList.toTypedArray()
            it.maxValue = 1
            it.wrapSelectorWheel = false
        }
    }

    private fun initConfirmClickListener() {
        binding.btnTodoEdit.setOnClickListener {
            when (reserveType) {
                TodoReserveType.CREATE_MODE -> {}
                TodoReserveType.EDIT_MODE -> {
                    arguments?.getParcelable<DialogBtnClickListener>(CONFIRM_ACTION)?.onConfirmClick()
                        ?: Timber.e(getString(R.string.null_point_exception_warning_dialog_argument))
                    editTodo()
                }
            }
            dismiss()
        }
    }

    private fun editTodo() {
        val date = dateList[binding.pickerTodoEditDate.value].replace(" ", "").split("/")
        val min = minList[binding.pickerTodoEditMinute.value]
        val ampm = ampmeList[binding.pickerTodoEditAmpm.value]
        val h = if (ampm == "PM") binding.pickerTodoEditHour.value + 12 else binding.pickerTodoEditHour.value
        val hour = if (h < 10) "0$h" else h
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val startAt = "$year-${date[0]}-${date[1]}T$hour:$min:00"

        val isCheckedAlarm = binding.switchBtnTodoEdit.isChecked
        val pushStatus = if (isCheckedAlarm) "ON" else "OFF"
        val body = TodoEditRequest(
            pushStatus = pushStatus,
            startAt = startAt
        )
        viewModel.putTodo(requireNotNull(todoReserveContent).id, body)
    }

    companion object {
        const val BOTTOM_SHEET_RESERVE = "bottomSheetReserve"
        const val RESERVE_TYPE = "reserveType"
        const val RESERVE_CONTENT = "reserveContent"
        const val CONFIRM_ACTION = "confirmAction"
    }
}
