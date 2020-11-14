package com.example.focusstarthomework.contacts.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.focusstarthomework.contacts.domain.entity.Contact
import kotlinx.android.synthetic.main.contact_list_item.view.*

class ContactListViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bind(contact: Contact) {
        itemView.contact_name.text = contact.name
        itemView.contact_number.text = contact.number
    }
}