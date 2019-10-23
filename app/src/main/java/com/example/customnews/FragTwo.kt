package com.example.customnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customnews.data.Post
import com.example.customnews.data.Results
import com.example.customnews.services.JsonPlaceHolderApi
import com.example.customnews.services.NewsRecyclerAdapter
import kotlinx.android.synthetic.main.frag_one.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import retrofit2.Response

class FragTwo : Fragment() {

    private var root: View? = null
    private lateinit var newsAdapter: NewsRecyclerAdapter
    private lateinit var job: Job

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflate the layout for this fragment
        root = inflater.inflate(R.layout.frag_two, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        addDataSet()

    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()

    }

    private suspend fun networkCall(): Response<Post> {
        return JsonPlaceHolderApi().getMostPopular()
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            newsAdapter = NewsRecyclerAdapter()
            adapter = newsAdapter
        }
    }

    private fun addDataSet() {
        job = CoroutineScope(IO).launch {
            val response = networkCall()
            if (response.isSuccessful) {
                val post = response.body()!!
                val data: List<Results> = post.results.reversed()
                updateOnMainThread(data)
            } else {
                Toast.makeText(activity, "Failed to retrieve items", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUi(input: List<Results>) {
        newsAdapter.updateNewsItems(input)
    }

    private suspend fun updateOnMainThread(input: List<Results>) {
        withContext(Main) {
            updateUi(input)
        }
    }
}