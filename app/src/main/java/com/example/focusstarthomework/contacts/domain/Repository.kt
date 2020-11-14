package com.example.focusstarthomework.contacts.domain

import com.example.focusstarthomework.contacts.domain.entity.ImageDTO

interface Repository {
    suspend fun uploadImage(imageDTO: ImageDTO)
}