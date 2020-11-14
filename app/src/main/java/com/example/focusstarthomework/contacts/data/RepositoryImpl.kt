package com.example.focusstarthomework.contacts.data

import com.example.focusstarthomework.contacts.domain.Repository
import com.example.focusstarthomework.contacts.domain.entity.ImageDTO

class RepositoryImpl(
    private val networkDataSource: NetworkDataSourceImpl
) : Repository {
    override suspend fun uploadImage(imageDTO: ImageDTO) {
        networkDataSource.uploadImage(imageDTO)
    }
}