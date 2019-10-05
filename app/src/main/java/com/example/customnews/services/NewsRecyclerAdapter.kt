package com.example.customnews.services

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.customnews.R
import com.example.customnews.data.Post
import com.example.customnews.data.Results
import kotlinx.android.synthetic.main.layout_news_list_item.view.*

class NewsRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<Results> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_news_list_item,
                parent,
                false
            )
        )
    }

    fun updateNewsItems(newList : List<Results>){
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NewsViewHolder -> holder.bind(items[position])
        }
    }

    /*//This is how we submit the Post list of data to the recyclerView adapter
    fun submitList(postList: List<Results>) {
        items = postList
    }*/

    //Creating a custom view holder, to reflect how my entries are going to look like
    class NewsViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val newsImage = itemView.image_view
        val newsSection = itemView.section_view
        val newsPublished = itemView.date_view
        val newsTitle = itemView.title_view

        fun bind(results: Results) {
            itemView.setOnClickListener{
                //Code for opening browser
                Toast.makeText(itemView.context,results.title, Toast.LENGTH_SHORT).show()
            }
            newsSection.text = results.section
            newsPublished.text = results.published
            newsTitle.text = results.title

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            if(!results.multimedia.isNullOrEmpty()) {

                Glide.with(itemView.context)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(if (results.multimedia.size>1 ) results.multimedia[1].url  else results.multimedia[0].url)
                    .into(newsImage)
            }
        }
    }
}