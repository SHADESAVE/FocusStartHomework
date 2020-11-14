package com.example.focusstarthomework.contacts.data.datasource

import com.example.focusstarthomework.contacts.data.datasource.local.contentprovider.ContentProvider
import com.example.focusstarthomework.contacts.data.datasource.local.database.ContactsDataBase
import com.example.focusstarthomework.contacts.domain.entity.Contact

interface LocalContactsDataSource {
    suspend fun getContactsFromPhone(): List<Contact>
    suspend fun getContactsFromDatabase(): List<Contact>
    suspend fun insertContactsInDatabase(contactListFromPhone: List<Contact>)
}

class LocalContactsDataSourceImpl(
    private val database: ContactsDataBase,
    private val contactsProvider: ContentProvider
) : LocalContactsDataSource {

    override suspend fun getContactsFromPhone(): List<Contact> =
        contactsProvider.getAllContacts()

    override suspend fun getContactsFromDatabase(): List<Contact> =
        database.contactsDao().getContacts()

    override suspend fun insertContactsInDatabase(contactListFromPhone: List<Contact>) {
        database.contactsDao().insertContacts(contactListFromPhone)
    }
}