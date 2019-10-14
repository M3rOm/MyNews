package com.example.customnews.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.customnews.R
import kotlinx.android.synthetic.main.activity_search.*

const val SEARCH_EXPRESSION = "com.example.customNews.MESSAGE"

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        //Called when user taps the SEARCH button
        start_search_button.setOnClickListener {
            sendSearchCriteria()
        }

    }

    private fun sendSearchCriteria() {
        if (validateCriteria()) {
            val searchFor = search_field.text.toString()
            val intent = Intent(this, SearchResults::class.java).apply {
                putExtra(SEARCH_EXPRESSION, searchFor)
            }
            startActivity(intent)
        } else {
            Toast.makeText(this, "Please check search conditions", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateCriteria(): Boolean {
        return (validateSearchField() && validateCheckBox())
        // validateDate()
    }

    private fun validateCheckBox(): Boolean {
        return search_checkBox_one.isChecked ||
                search_checkBox_two.isChecked ||
                search_checkBox_three.isChecked ||
                search_checkBox_four.isChecked ||
                search_checkBox_five.isChecked ||
                search_checkBox_six.isChecked
    }

    /*  private fun validateDate(): Boolean {
          return (begin_date.text <= end_date.text)
      }*/

    private fun validateSearchField(): Boolean {
        return if (search_field.text.isBlank()) {
            Toast.makeText(this, "Please enter a search expression", Toast.LENGTH_SHORT).show()
            false
        } else true
    }
}
