package com.mustafacol.spacex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import com.apollographql.apollo3.ApolloClient

class MainActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progress_bar_main)
    }

    fun setProgressBarVisibility(visibility: Int) {
        progressBar.visibility = visibility
    }
}