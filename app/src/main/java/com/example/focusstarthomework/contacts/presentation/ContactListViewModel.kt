package com.example.focusstarthomework.contacts.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.focusstarthomework.contacts.domain.GetContactsUseCase
import com.example.focusstarthomework.contacts.domain.entity.Contact
import kotlinx.coroutines.launch

class ContactListViewModel(
    getContactsUseCase: GetContactsUseCase
) : ViewModel() {

    val contacts = MutableLiveData<List<Contact>>()

    init {
        viewModelScope.launch {
            contacts.value = getContactsUseCase()
        }
    }
}