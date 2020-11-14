package com.example.focusstarthomework.contacts.data

import com.example.focusstarthomework.contacts.data.datasource.LocalContactsDataSourceImpl
import com.example.focusstarthomework.contacts.domain.ContactsRepository
import com.example.focusstarthomework.contacts.domain.entity.Contact

class ContactsRepositoryImpl(
    private val localContactsDataSourceImpl: LocalContactsDataSourceImpl

) : ContactsRepository {

    override suspend fun getContactsFromPhone(): List<Contact> =
        localContactsDataSourceImpl.getContactsFromPhone()

    override suspend fun getContactsFromDatabase(): List<Contact> =
        localContactsDataSourceImpl.getContactsFromDatabase()

    override suspend fun insertContactsInDatabase(contactListFromPhone: List<Contact>) {
        localContactsDataSourceImpl.insertContactsInDatabase(contactListFromPhone)
    }
}