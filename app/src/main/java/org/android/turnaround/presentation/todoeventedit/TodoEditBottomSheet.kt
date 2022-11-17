package org.android.turnaround.presentation.todoeventedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.android.turnaround.R
import org.android.turnaround.data.remote.entity.request.TodoEditRequest
import org.android.turnaround.databinding.BottomSheetTodoEditBinding
import org.android.turnaround.domain.entity.Todo
import org.android.turnaround.util.EventObserver
import java.text.SimpleDateFormat
import java.util.*

class TodoEditBottomSheet(val viewModel: TodoEventEditViewModel, val todo: Todo) : BottomSheetDialogFragment() {
    private var _binding: BottomSheetTodoEditBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))

    val dateList = mutableListOf<String>()
    val minList = mutableListOf<String>()
    val ampmeList = mutableListOf<String>("PM", "AM")

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
        binding.todo = todo
        initDatePicker()
        initHourPicker()
        initMinutePicker()
        initAmPmPicker()
        initIsCheckedBottomSheetTodoEditBtnEventObserver()
    }

    private fun initDatePicker() {
        val today = Calendar.getInstance()
        val sdf = SimpleDateFormat("MM/dd")
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
            if(i % 5 == 0) minList.add(if(i < 10) "0$i" else i.toString())
        }
        binding.pickerTodoEditMinute.let {
            it.minValue = 0
            it.displayedValues = minList.toTypedArray()
            it.maxValue = minList.size-1
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

    private fun initIsCheckedBottomSheetTodoEditBtnEventObserver() {
        viewModel.isClickedBottomSheetTodoEditBtnEvent.observe(
            viewLifecycleOwner,
            EventObserver {
                editTodo()
                dismiss()
            }
        )
    }

    private fun editTodo() {
        val date = dateList[binding.pickerTodoEditDate.value].split("/")
        val min = minList[binding.pickerTodoEditMinute.value]
        val ampm = ampmeList[binding.pickerTodoEditAmpm.value]
        val hour = if(ampm == "PM") binding.pickerTodoEditHour.value + 12 else binding.pickerTodoEditHour.value
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val startAt = "${year}-${date[0]}-${date[1]}T${hour}:${min}:00"

        val isCheckedAlarm = binding.switchBtnTodoEdit.isChecked
        val pushStatus = if(isCheckedAlarm) "ON" else "OFF"
        val body = TodoEditRequest(
            pushStatus = pushStatus,
            startAt = startAt
        )
        viewModel.putTodo(todo.todoId, body)
    }

}
