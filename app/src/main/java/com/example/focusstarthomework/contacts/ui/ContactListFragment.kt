package com.example.focusstarthomework.contacts.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.focusstarthomework.R
import com.example.focusstarthomework.contacts.di.ContactListViewModelFactory
import com.example.focusstarthomework.contacts.presentation.ContactListViewModel
import kotlinx.android.synthetic.main.fragment_contact_list.*

const val REQUEST_CONTACTS_CODE = 1

class ContactListFragment : Fragment(R.layout.fragment_contact_list) {

    // Ленивая инициализация вью-модели с фабрикой
    private val viewModel: ContactListViewModel by viewModels {
        ContactListViewModelFactory(requireContext())
    }

    private val adapter = ContactListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contact_list_recycler.layoutManager = LinearLayoutManager(requireContext())
        contact_list_recycler.adapter = adapter
        contact_list_recycler.setHasFixedSize(true)

        loadContacts()
    }

    private fun loadContacts() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CONTACTS_CODE)
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    REQUEST_CONTACTS_CODE
                )
            }
        } else {
            contact_permission_failure_text.visibility = View.GONE
            viewModel.contacts.observe(viewLifecycleOwner, Observer {
                adapter.setContactList(it)
            })
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CONTACTS_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadContacts()
            }
        }
    }
}