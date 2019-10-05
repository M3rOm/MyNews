package com.example.customnews.data

import com.google.gson.annotations.SerializedName

data class Results(

    val section: String,
    val subsection: String,
    var title: String,
    @SerializedName("abstract")
    val text: String,
    @SerializedName("created_date")
    val published: String,
    val multimedia: List<Multimedia>
)
