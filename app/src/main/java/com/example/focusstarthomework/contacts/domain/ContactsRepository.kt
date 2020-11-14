package com.example.focusstarthomework.contacts.domain

import com.example.focusstarthomework.contacts.domain.entity.Contact

interface ContactsRepository {
    suspend fun getContactsFromPhone(): List<Contact>
    suspend fun getContactsFromDatabase(): List<Contact>
    suspend fun insertContactsInDatabase(contactListFromPhone: List<Contact>)
}