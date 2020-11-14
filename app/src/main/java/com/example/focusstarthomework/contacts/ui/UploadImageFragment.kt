package com.example.focusstarthomework.contacts.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.focusstarthomework.R
import com.example.focusstarthomework.contacts.di.UploadImageViewModelFactory
import com.example.focusstarthomework.contacts.domain.entity.ImageDTO
import com.example.focusstarthomework.contacts.presentation.UploadImageViewModel
import com.example.focusstarthomework.utils.toBase64String
import kotlinx.android.synthetic.main.fragment_upload_image.*
import java.io.File

const val READ_STORAGE_PERMISSION_CODE = 1
const val IMAGE_PICK_CODE = 2

class ContactListFragment : Fragment(R.layout.fragment_upload_image) {

    // Ленивая инициализация вью-модели с фабрикой
    private val viewModel: UploadImageViewModel by viewModels {
        UploadImageViewModelFactory()
    }

    private var encodedImage: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.chooseImageEvent.observe(viewLifecycleOwner, Observer {
            imageChosen()
        })

        viewModel.uploadImageEvent.observe(viewLifecycleOwner, Observer {
            viewModel.uploadImage(
                ImageDTO(
                    image = encodedImage,
                    name = name_edit_text.text.toString(),
                    title = title_edit_text.text.toString(),
                    description = description_edit_text.text.toString()
                )
            )
        })

        choose_image_button.setOnClickListener {
            viewModel.chooseImageEvent()
        }

        upload_image_button.setOnClickListener {
            viewModel.uploadImageEvent()
        }
    }

    private fun imageChosen() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_STORAGE_PERMISSION_CODE
            )
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_STORAGE_PERMISSION_CODE
                )
            }
        } else {
            read_storage_permission_failure_text.visibility = View.GONE
            startActivityForResult(Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            }, IMAGE_PICK_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == READ_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                imageChosen()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            data?.data?.let {
                chosen_image.setImageURI(it)
                encodedImage = it.toBase64String(requireContext().contentResolver)
            }
            choose_image_button.text = "Выбрать другую"
            upload_image_button.visibility = View.VISIBLE
            name_title_layout.visibility = View.VISIBLE
            description_edit_text.visibility = View.VISIBLE
        }
    }
}