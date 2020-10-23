package com.example.focusstarthomework.utils

import android.app.Application
import android.provider.ContactsContract
import com.example.focusstarthomework.ui.fragments.A.domain.entity.Contact

fun getAllContacts(app: Application?): List<Contact> {

    val contactList = mutableListOf<Contact>()

    val cursor = app?.contentResolver?.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)

    while (cursor?.moveToNext()!!) {
        contactList.add(Contact(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))))
    }

    cursor.close()

    return contactList
}