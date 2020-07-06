package com.example.orangeapplication.data.model.program

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Contents(
    var title: List<Title>,
    var subtitle: String,
    var imageurl: String,
    var fullscreenimageurl: String,
    var id: String,
    var detaillink: String
) : Parcelable