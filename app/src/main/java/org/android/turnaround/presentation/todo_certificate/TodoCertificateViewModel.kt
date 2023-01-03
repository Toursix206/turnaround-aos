package org.android.turnaround.presentation.todo_certificate

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import org.android.turnaround.domain.repository.TodoRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TodoCertificateViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {
    private var todoId = -1

    private val _imgUriList = mutableListOf<Uri?>()
    val imgUriList get() = _imgUriList

    private val _imgUri = MutableStateFlow(Uri.EMPTY)
    val imgUri: StateFlow<Uri?> = _imgUri.asStateFlow()

    private var imgMultiPart: MultipartBody.Part? = null

    private val _doneReviewId = MutableSharedFlow<Int>()
    val doneReviewId: SharedFlow<Int> = _doneReviewId.asSharedFlow()

    fun initTodoId(todoId: Int) {
        this.todoId = todoId
    }

    fun initImgUri(uri: Uri?) {
        _imgUriList.add(uri)
        _imgUri.value = uri
    }

    fun initImgMultiPart(img: MultipartBody.Part?) {
        imgMultiPart = img
    }

    fun postTodoCertificate() {
        viewModelScope.launch {
            todoRepository.postTodoCertificate(
                todoId = todoId,
                image = requireNotNull(imgMultiPart)
            ).onSuccess { response ->
                _doneReviewId.emit(response.doneReviewId)
            }.onFailure { Timber.d(it.message.toString()) }
        }
    }
}
