package com.example.customnews.services

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.customnews.R
import com.example.customnews.data.Results
import kotlinx.android.synthetic.main.layout_news_list_item.view.*
import java.text.SimpleDateFormat

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

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NewsViewHolder -> holder.bind(items[position])
        }
    }

    //Creating a custom view holder, to reflect how my entries are going to look like in Top Stories and Business tabs
    class NewsViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val newsImage = itemView.image_view
        val newsSection = itemView.section_view
        val newsPublished = itemView.date_view
        val newsTitle = itemView.title_view
        fun bind(results: Results) {
            itemView.setOnClickListener {
                //Code for opening browser
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(results.url))
                itemView.context.startActivity(webIntent)

            }
            newsSection.text = results.section
            //Parsing the date pattern("yyyy-MM-dd'T'HH:mm:ssZ")
            //Create our final date format
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            //Create our incoming date format
            val readDateFormatLong = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            val readDateFormatShort = SimpleDateFormat("yyyy-MM-dd")
            //Read the time info and create a Date object
            if (results.published != null) {
                val date = readDateFormatLong.parse(results.published)
                val dateParsed = dateFormat.format(date)
                newsPublished.text = dateParsed
                newsTitle.text = results.title
            } else {
                val date = readDateFormatShort.parse(results.published_date)
                val dateParsed = dateFormat.format(date)
                newsPublished.text = dateParsed
                newsTitle.text = results.title
            }


            val requestOptions = RequestOptions()
                .placeholder(R.drawable.thetimes)
                .error(R.drawable.thetimes)
                .fallback(R.drawable.thetimes)


            //Check for image directories and files
            if (results.multimedia.isNullOrEmpty()) {
                if (results.media.isNullOrEmpty()) {
                    //Use dummy image
                    Glide.with(itemView.context)
                        .applyDefaultRequestOptions(requestOptions)
                        .load(R.drawable.thetimes)
                        .into(newsImage)
                } else {    //Use image from most shared structure
                    val listOfMedia = results.media
                    Log.d("Tab", listOfMedia)
//                    Glide.with(itemView.context)
//                        .applyDefaultRequestOptions(requestOptions)
//                        .load(if (results.media[0].mediaMeta.size > 1) results.media[0].mediaMeta[1].url else results.media[0].mediaMeta[0].url)
//                        .into(newsImage)
                }

            } else {    //Use image from default structure
                Glide.with(itemView.context)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(if (results.multimedia.size > 1) results.multimedia[1].url else results.multimedia[0].url)
                    .into(newsImage)

            }
        }

    }

    fun updateNewsItems(newList: List<Results>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

}
