package com.example.customnews.services

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.customnews.R
import com.example.customnews.data.Docs
import com.example.customnews.data.Response
import com.example.customnews.data.Results
import kotlinx.android.synthetic.main.layout_news_list_item.view.*
import java.text.SimpleDateFormat

class SearchRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<Docs> = ArrayList()

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

    //Creating a custom view holder, to reflect how my entries are going to look like in the SearchResults activity
    class NewsViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val newsImage = itemView.image_view
        val newsSection = itemView.section_view
        val newsPublished = itemView.date_view
        val newsTitle = itemView.title_view
        fun bind(docs: Docs) {
            itemView.setOnClickListener {
                //Code for opening browser
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(docs.url))
                itemView.context.startActivity(webIntent)

            }
            newsSection.text = docs.section
            newsTitle.text = docs.headline[0].main
            //Parsing the date pattern("yyyy-MM-dd'T'HH:mm:ssZ")
            //Create our final date format
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            //Create our incoming date format
            val readDateFormatLong = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            val readDateFormatShort = SimpleDateFormat("yyyy-MM-dd")
            //Read the time info and create a Date object
            if (docs.pub_date != null) {
                val date = readDateFormatLong.parse(docs.pub_date)
                val dateParsed = dateFormat.format(date)
                newsPublished.text = dateParsed
                newsTitle.text = docs.headline[0].main
            }
//            else {
//                val date = readDateFormatShort.parse(results.published_date)
//                val dateParsed = dateFormat.format(date)
//                newsPublished.text = dateParsed
//                newsTitle.text = results.title
//            }


            val requestOptions = RequestOptions()
                .placeholder(R.drawable.thetimes)
                .error(R.drawable.thetimes)
                .fallback(R.drawable.thetimes)


            //Check for image directories and files
            if (docs.multimedia.isNullOrEmpty()) {
                //Use dummy image
                Glide.with(itemView.context)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(R.drawable.thetimes)
                    .into(newsImage)
            } else {    //Use image from most source
                Glide.with(itemView.context)
                    .applyDefaultRequestOptions(requestOptions)
                    .load(docs.multimedia[0].url)
                    .into(newsImage)
            }

        }
    }

    fun updateNewsItems(newList: List<Docs>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

}
