package org.android.turnaround.presentation.todo_certificate

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TodoCertificateViewModel : ViewModel() {
    private val _imgUri = MutableStateFlow(Uri.EMPTY)
    val imgUri: StateFlow<Uri?> = _imgUri.asStateFlow()

    fun initImgUri(uri: Uri?) {
        _imgUri.value = uri
    }
}
