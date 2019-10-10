package com.example.customnews.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.customnews.FragOne
import com.example.customnews.R
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        start_search_button.setOnClickListener {
        val intent = Intent(this, SearchResults::class.java)
            startActivity(intent)
        }

//        val cb1 = R.id.search_checkBox_one
//        val cb2 = R.id.search_checkBox_two
//        val cb3 = R.id.search_checkBox_three
//        val cb4 = R.id.search_checkBox_four
//        val cb5 = R.id.search_checkBox_five
//        val cb6 = R.id.search_checkBox_six
//        val beginDate = R.id.begin_date
//        val endDate = R.id.end_date
//        val field = R.id.search_field

    }
}
