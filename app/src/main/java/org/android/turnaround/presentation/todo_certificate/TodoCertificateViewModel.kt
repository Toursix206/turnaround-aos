package org.android.turnaround.presentation.todo_certificate

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.android.turnaround.domain.repository.TodoRepository
import javax.inject.Inject

@HiltViewModel
class TodoCertificateViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {
    private val _imgUri = MutableStateFlow(Uri.EMPTY)
    val imgUri: StateFlow<Uri?> = _imgUri.asStateFlow()

    fun initImgUri(uri: Uri?) {
        _imgUri.value = uri
    }

    fun postTodoCertificate() {
        viewModelScope.launch {
            // todoRepository.postTodoCertificate()
        }
    }
}
