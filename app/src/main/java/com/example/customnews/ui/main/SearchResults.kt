package com.example.customnews.ui.main

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customnews.R
import com.example.customnews.data.Docs
import com.example.customnews.data.Post
import com.example.customnews.data.Results
import com.example.customnews.services.JsonPlaceHolderApi
import com.example.customnews.services.NewsRecyclerAdapter
import com.example.customnews.services.SearchRecyclerAdapter
import kotlinx.android.synthetic.main.frag_one.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class SearchResults : AppCompatActivity() {

    //private var root: View? = null
    private lateinit var searchAdapter: SearchRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.frag_one)

        initRecyclerView()
        addDataSet()
    }


    private suspend fun networkCall(): Response<Post> {
        return JsonPlaceHolderApi().getSearchResults("time","news_desk:Technology")
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this.context)
            searchAdapter = SearchRecyclerAdapter()
            adapter = searchAdapter
        }
    }

    private fun addDataSet() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = networkCall()
            if (response.isSuccessful) {
                val post = response.body()!!
                val data = post.response[0].docs
                updateOnMainThread(data)
            } else {
                Toast.makeText(this@SearchResults, "Failed to retrieve items", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    private fun updateUi(input: List<Docs>) {
        searchAdapter.updateNewsItems(input)
    }

    private suspend fun updateOnMainThread(input: List<Docs>) {
        withContext(Dispatchers.Main) {
            updateUi(input)
        }
    }
}