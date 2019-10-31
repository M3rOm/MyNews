package com.example.customnews.ui.main

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.customnews.R
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.toolbar_activity_search
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

const val SEARCH = "com.example.customNews.SEARCH"

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        //Initialize toolbar
        toolbar_activity_search.title = getString(R.string.search_activity_name)
        //Initialize shared preference handle
        val sharedPref = getSharedPreferences(SEARCH, Context.MODE_PRIVATE)
        //Get today's date
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        //Show it as default end date
        end_date.setText(getString(R.string.date_format_to_show,day,month+1,year))
        //Default begin date is one year less
        begin_date.setText(getString(R.string.date_format_to_show,day,month+1,year-1))
        //Create necessary date formats
        val alphaDateFormat = SimpleDateFormat("dd/MM/yyyy")
        val requiredDateFormat = SimpleDateFormat("yyyyMMdd")
        val editor = sharedPref.edit()
        //Remember the last searched expression
        if (sharedPref.getString("expression","") != null) search_field.setText(sharedPref.getString("expression",""))
        //Called when user taps on Begin Date field
        begin_date.setOnClickListener {
            val myDatePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay ->
                    //set text in editText
                    begin_date.setText(getString(R.string.date_format_to_show,mDay,mMonth+1,mYear))
                    //save a calendar type
                    val chosenDate = alphaDateFormat.parse(begin_date.text.toString())
                    val beginDateToSave = requiredDateFormat.format(chosenDate)
                    //save it in shared preferences
                    editor.putString("begin", beginDateToSave)
                },
                year - 1,
                month,
                day
            )
            //show dialog
            myDatePickerDialog.show()
        }
        //Called when user taps on End Date field
        end_date.setOnClickListener {
            val myDatePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _,mYear, mMonth, mDay->
                    //set text in editText
                    end_date.setText(getString(R.string.date_format_to_show,mDay,mMonth+1,mYear))
                    //save a calendar type
                    val chosenDate = alphaDateFormat.parse(end_date.text.toString())
                    val endDateToSave = requiredDateFormat.format(chosenDate)
                    //save it in shared preferences
                    editor.putString("end", endDateToSave)
                },
                year,
                month,
                day
            )
            //show dialog
            myDatePickerDialog.show()
        }

        //Called when user taps the SEARCH button
        start_search_button.setOnClickListener {
            if (validateCriteria()) {
                editor.putString("expression", search_field.text.toString())
                editor.putString("locations", formatter(doSearchIn()))
                editor.apply()
                //Starts the SearchResults activity
                val intent = Intent(this, SearchResults::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please check search conditions", Toast.LENGTH_SHORT).show()
            }
        }

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

    //This creates an array list of Strings populated by the topics which the user wants to search in
    private fun doSearchIn(): ArrayList<String?> {
        val environment = search_checkBox_one.text.toString()
        val technology = search_checkBox_two.text.toString()
        val movies = search_checkBox_three.text.toString()
        val business = search_checkBox_four.text.toString()
        val politics = search_checkBox_five.text.toString()
        val classifieds = search_checkBox_six.text.toString()
        val searchIn = arrayListOf<String?>()
        if (search_checkBox_one.isChecked) searchIn += environment
        if (search_checkBox_two.isChecked) searchIn += technology
        if (search_checkBox_three.isChecked) searchIn += movies
        if (search_checkBox_four.isChecked) searchIn += business
        if (search_checkBox_five.isChecked) searchIn += politics
        if (search_checkBox_six.isChecked) searchIn += classifieds
        return searchIn
    }

    //This function validates that the user is sending a valid search
    private fun validateCriteria(): Boolean {
        return (validateSearchField() && validateCheckBox() && validateDate())
    }

    //Validates the checkboxes specifically
    private fun validateCheckBox(): Boolean {
        return if (!(search_checkBox_one.isChecked ||
                    search_checkBox_two.isChecked ||
                    search_checkBox_three.isChecked ||
                    search_checkBox_four.isChecked ||
                    search_checkBox_five.isChecked ||
                    search_checkBox_six.isChecked)
        ) {
            Toast.makeText(this, "Check at least one topic", Toast.LENGTH_SHORT).show()
            false
        } else true
    }

    private fun validateDate(): Boolean {
        val sharedPref = getSharedPreferences(SEARCH, Context.MODE_PRIVATE)
        val begin = sharedPref.getString("begin", "20180101")?:"20180101"
        val end = sharedPref.getString("end", "20190101")?:"20190101"
        val num = end.toInt()
        val num2 = begin.toInt()
        return (num2 <= num)
    }

    //Validates the search field specifically
    private fun validateSearchField(): Boolean {
        return if (search_field.text.isBlank()) {
            Toast.makeText(this, "Please enter a search expression", Toast.LENGTH_SHORT).show()
            false
        } else true
    }
}
