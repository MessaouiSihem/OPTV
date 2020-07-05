package com.example.orangeapplication.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Title(
    var value: String,
    var type: String
): Parcelable