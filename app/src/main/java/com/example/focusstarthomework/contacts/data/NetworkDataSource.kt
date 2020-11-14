package com.example.focusstarthomework.contacts.data

import com.example.focusstarthomework.contacts.domain.entity.ImageDTO
import okhttp3.MultipartBody

interface NetworkDataSource {
    suspend fun uploadImage(imageDTO: ImageDTO)
}

class NetworkDataSourceImpl(private val api: Api) : NetworkDataSource {
    override suspend fun uploadImage(imageDTO: ImageDTO) {

        api.uploadImage(
            MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("image", imageDTO.image)
                .addFormDataPart("name", imageDTO.name)
                .addFormDataPart("title", imageDTO.title)
                .addFormDataPart("description", imageDTO.description)
                .build()
        )
    }
}