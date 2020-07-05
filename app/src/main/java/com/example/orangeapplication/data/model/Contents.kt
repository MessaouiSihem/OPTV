package com.example.orangeapplication.data.model


data class Contents(
    var title: List<Title>,
    var subtitle: String,
    var imageurl: String,
    var fullscreenimageurl: String,
    var id: String,
    var detaillink: String
)