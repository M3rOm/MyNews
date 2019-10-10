package com.example.customnews.data

import com.google.gson.annotations.SerializedName

data class Docs(
    @SerializedName("web_url")
    val url: String,
    val snippet: String,
    val multimedia: List<Multimedia>,
    val headline: List<Headline>,
    val pub_date: String,
    @SerializedName("news_desk")
    val section: String,
    val section_name: String

)