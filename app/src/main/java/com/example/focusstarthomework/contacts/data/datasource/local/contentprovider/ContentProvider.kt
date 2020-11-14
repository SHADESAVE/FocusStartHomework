package com.example.focusstarthomework.contacts.data.datasource.local.contentprovider

import android.content.ContentResolver
import android.provider.ContactsContract
import com.example.focusstarthomework.contacts.domain.entity.Contact

class ContentProvider(private val contentResolver: ContentResolver) {

    fun getAllContacts(): List<Contact> {
        val contactList = mutableListOf<Contact>()
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        cursor?.use {
            while (it.moveToNext()) {
                contactList.add(
                    Contact(
                        it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
                        it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    )
                )
            }
        }

        return contactList
    }
}
