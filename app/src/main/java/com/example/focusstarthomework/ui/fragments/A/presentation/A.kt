package com.example.focusstarthomework.ui.fragments.A.presentation

import android.Manifest
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.focusstarthomework.CounterWorker
import com.example.focusstarthomework.R
import com.example.focusstarthomework.utils.getAllContacts
import kotlinx.android.synthetic.main.fragment_a.*

const val REQUEST_CODE = 1

class A : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_a, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            activity?.finish()
        }

        a_button.setOnClickListener {
            it.findNavController().navigate(R.id.action_A_to_B)
        }

        count_button.setOnClickListener {
            context?.let { context ->
                val uploadWorkRequest: WorkRequest =
                    OneTimeWorkRequestBuilder<CounterWorker>().build()
                WorkManager.getInstance(context).enqueue(uploadWorkRequest)
            }
        }

        get_contacts_button.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    view.context,
                    Manifest.permission.READ_CONTACTS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                activity?.let { activity ->
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(Manifest.permission.READ_CONTACTS),
                        REQUEST_CODE
                    )
                }
            } else {
                setContacts()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        context?.let { WorkManager.getInstance(it).cancelAllWork() }
        val notificationManager =
            activity?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE) setContacts()
    }

    private fun setContacts() {
        val adapter = ContactsAdapter()
        adapter.setContacts(getAllContacts(activity?.application))
        contacts_recycler.layoutManager = LinearLayoutManager(view?.context)
        contacts_recycler.setHasFixedSize(true)
        contacts_recycler.adapter = adapter
    }
}