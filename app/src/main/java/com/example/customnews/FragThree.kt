package com.example.customnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.example.customnews.data.Post
import com.example.customnews.services.JsonPlaceHolderApi
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.frag_one.*
import kotlinx.android.synthetic.main.frag_three.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import retrofit2.Response

class FragThree : Fragment() {
    private var textView: TextView? = textViewFrag3
    private var root: View? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflate the layout for this fragment
        root = inflater.inflate(R.layout.frag_three, container, false)

        CoroutineScope(IO).launch {
            val response = networkCall()
            if (response.isSuccessful) {
                val post = response.body()!!
                var output = ""
                output += post.results[0].section
                output += "\n"
                output += post.results[0].subsection
                output += "\n"
                output += post.results[0].title
                output += "\n"
                output += post.results[0].text
                output += "\n"
                updateOnMainThread(output)
            } else {
                updateOnMainThread("Failed to retrieve items")
            }
        }
        return root
    }

    private suspend fun networkCall(): Response<Post> {
        return JsonPlaceHolderApi().getPostList()
    }


    private fun updateUi(input: String) {
        val newText = textView?.text.toString() + "\n$input"
        textView?.text = newText
    }

    private suspend fun updateOnMainThread(input: String) {
        withContext(Main) {
            updateUi(input)
        }
    }


}