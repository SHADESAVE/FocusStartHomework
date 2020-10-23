package com.example.focusstarthomework.ui.fragments.A.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.focusstarthomework.R
import com.example.focusstarthomework.ui.fragments.A.domain.entity.Contact
import kotlinx.android.synthetic.main.contacts_list_item.view.*

class ContactsAdapter() : RecyclerView.Adapter<ContactsViewHolder>() {

    private val contactsList: MutableList<Contact> = mutableListOf()

    fun setContacts(newContactsList: List<Contact>) {
        contactsList.clear()
        contactsList.addAll(newContactsList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int =
        contactsList.size

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bind(contactsList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder =
        ContactsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.contacts_list_item, parent, false))
}

class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun  bind(contact: Contact) {
        itemView.contact_name.text = contact.name
        itemView.phone_number.text = contact.phoneNumber
    }
}