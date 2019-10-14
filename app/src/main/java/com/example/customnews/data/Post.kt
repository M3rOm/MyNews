package com.example.customnews.data

data class Post (

     val copyright : String,
     val last_updated :String,
     val num_results : Int,
     val results :List<Results>,
     val section : String,
     val status : String,
     val response: Response
)