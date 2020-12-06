package com.example.focusstarthomework.loanslist.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoanView(
    val amountPercentPeriod: String,
    val date: String,
    val fullName: String,
    val id: Long,
    val phoneNumber: String,
    val state: String,
    val stateColor: Int
) : Parcelable