package org.android.turnaround.presentation.signup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignUpViewModel : ViewModel() {
    private val _selectedProfile = MutableStateFlow(0)
    val selectedProfile: StateFlow<Int> = _selectedProfile.asStateFlow()

    fun initSelectedProfile(index: Int) {
        _selectedProfile.value = index
    }
}
