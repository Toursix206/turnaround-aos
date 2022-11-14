package org.android.turnaround.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.turnaround.util.Event

class HomeViewModel : ViewModel() {
    private val _openTodoEvent = MutableLiveData<Event<Unit>>()
    val openTodoEvent: LiveData<Event<Unit>> = _openTodoEvent

    fun openTodoEvent() {
        _openTodoEvent.value = Event(Unit)
    }
}
