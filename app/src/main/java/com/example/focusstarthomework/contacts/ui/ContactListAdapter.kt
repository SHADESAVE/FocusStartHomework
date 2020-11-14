package com.example.focusstarthomework.contacts.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.focusstarthomework.R
import com.example.focusstarthomework.contacts.domain.entity.Contact

class ContactListAdapter() : RecyclerView.Adapter<ContactListViewHolder>() {

    private var contactList: MutableList<Contact> = mutableListOf()

    fun setContactList(contactList: List<Contact>) {
        this.contactList.clear()
        this.contactList.addAll(contactList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListViewHolder =
        ContactListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.contact_list_item, parent, false)
        )

    override fun getItemCount() = contactList.size

    override fun onBindViewHolder(holder: ContactListViewHolder, position: Int) {
        holder.bind(contactList[position])
    }
}
