package com.example.focusstarthomework.contacts.presentation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focusstarthomework.contacts.domain.UploadImageUseCase
import com.example.focusstarthomework.contacts.domain.entity.ImageDTO
import com.example.focusstarthomework.contacts.ui.IMAGE_PICK_CODE
import com.example.focusstarthomework.contacts.ui.READ_STORAGE_PERMISSION_CODE
import com.example.focusstarthomework.utils.SingleLiveEvent
import kotlinx.android.synthetic.main.fragment_upload_image.*
import kotlinx.coroutines.launch

class UploadImageViewModel(
    private val uploadImageUseCase: UploadImageUseCase
) : ViewModel() {

    val chooseImageEvent = SingleLiveEvent<Unit>()
    val uploadImageEvent = SingleLiveEvent<Unit>()

    fun chooseImageEvent() {
        chooseImageEvent(Unit)
    }

    fun uploadImageEvent() {
        uploadImageEvent(Unit)
    }

    fun uploadImage(imageDTO: ImageDTO) {
        viewModelScope.launch {
            try {
//            progressBar.value = Status.LOADING
                uploadImageUseCase(imageDTO)
//            progressStatus.value = Status.DONE
            } catch (e: Exception) {
                Log.e("uploadImageError: ", e.toString())
//            progressStatus.value = Status.ERROR
            }
        }
    }
}