package com.example.pokesearch

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.example.pokesearch.api.PokemonApi
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val TAG = "find"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }
}
