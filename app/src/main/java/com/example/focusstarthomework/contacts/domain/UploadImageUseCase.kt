package com.example.focusstarthomework.contacts.domain

import com.example.focusstarthomework.contacts.domain.entity.ImageDTO


class UploadImageUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(imageDTO: ImageDTO) {
        repository.uploadImage(imageDTO)
    }
}