package com.example.focusstarthomework.contacts.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.focusstarthomework.contacts.data.ContactsRepositoryImpl
import com.example.focusstarthomework.contacts.data.datasource.LocalContactsDataSourceImpl
import com.example.focusstarthomework.contacts.data.datasource.local.contentprovider.ContentProvider
import com.example.focusstarthomework.contacts.data.datasource.local.database.ContactsDataBase
import com.example.focusstarthomework.contacts.domain.GetContactsUseCase
import com.example.focusstarthomework.contacts.presentation.ContactListViewModel

class ContactListViewModelFactory(
    private val context: Context

) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        val database = ContactsDataBase.getInstance(context)

        if (modelClass == ContactListViewModel::class.java) {
            val localDataSource = LocalContactsDataSourceImpl(database, ContentProvider(context.contentResolver))
            val contactsRepository = ContactsRepositoryImpl(localDataSource)
            val getContactsUseCase = GetContactsUseCase(contactsRepository)

            return ContactListViewModel(getContactsUseCase) as T
        } else {
            error("Unexpected class $modelClass")
        }
    }
}