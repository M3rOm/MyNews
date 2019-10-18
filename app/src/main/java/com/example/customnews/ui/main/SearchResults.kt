package com.example.customnews.ui.main

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customnews.R
import com.example.customnews.data.Docs
import com.example.customnews.data.Post
import com.example.customnews.services.JsonPlaceHolderApi
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

    //Specifying the type of the network call
    private suspend fun networkCall(): Response<Post> {
        val sharedPref = getSharedPreferences(SEARCH_DATES, Context.MODE_PRIVATE)
        val beginDate = sharedPref.getString("begin", "20180101")
        val endDate = sharedPref.getString("end", "20190101")
        val searchTerm = intent.getStringExtra(SEARCH_EXPRESSION)
        val searchLocation = intent.getStringArrayListExtra(SEARCH_SECTIONS)
        return JsonPlaceHolderApi().getSearchResults(
            searchTerm,
            formatter(searchLocation),
            "newest",
            beginDate,
            endDate
        )
    }
    //Creates the lucene compatible field grouping string for the api search
    private fun formatter(searchLocation: ArrayList<String?>): String {
        var result = "news_desk:(${searchLocation[0]}"
        for (item in searchLocation) {
            item?.let {
                result += " || $item"
            }
        }
        result += ")"
        return result
    }
    //Builds the recycler view for the articles
    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this.context)
            searchAdapter = SearchRecyclerAdapter()
            adapter = searchAdapter
        }
    }
    //Background task for the network operation of fetching the data from the server
    private fun addDataSet() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = networkCall()
            if (response.isSuccessful) {
                //On successful network call, update the UI
                val post = response.body()!!
                val data = post.response.docs
                updateOnMainThread(data)
            } else {
                //On failed network call, let the user know
                toastOnMainThread("Failed to retrieve items")
            }
        }
    }
    //Update the UI on the main thread
    private suspend fun updateOnMainThread(input: List<Docs>) {
        withContext(Dispatchers.Main) {
            updateUi(input)
        }
    }

    private fun updateUi(input: List<Docs>) {
        //Show the articles on the screen using recycler view
        searchAdapter.updateNewsItems(input)
        //Let user know if the search has not given any results
        if (searchAdapter.itemCount == 0) {
            Toast.makeText(
                this,
                "No articles have been found for the given criteria",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    //Show a toast using main thread
    private suspend fun toastOnMainThread(s: String) {
        withContext(Dispatchers.Main) {
            updateToast(s)
        }
    }

    private fun updateToast(s: String) {
        Toast.makeText(this@SearchResults, s, Toast.LENGTH_SHORT)
            .show()
    }
}