package com.example.focusstarthomework.contacts.domain

import com.example.focusstarthomework.contacts.domain.entity.Contact

class GetContactsUseCase(
    private val contactsRepository: ContactsRepository
) {

    suspend operator fun invoke(): List<Contact> {
        val contactListFromPhone = contactsRepository.getContactsFromPhone()
        contactsRepository.insertContactsInDatabase(contactListFromPhone)
        return contactsRepository.getContactsFromDatabase()
    }
}