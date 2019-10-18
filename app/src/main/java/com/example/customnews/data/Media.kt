package com.example.customnews.data

import com.google.gson.annotations.SerializedName

data class Media (
    @SerializedName ("media-metadata")
       val mediaMeta: List<MediaMeta>?
)