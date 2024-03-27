package com.example.multiactivity

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView


class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val message = intent.getStringExtra("MESSAGE")
        val textView = findViewById<TextView>(R.id.textView)
        textView.text = message
    }
    override fun onStart() {
        super.onStart()
        Log.i(ContentValues.TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(ContentValues.TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(ContentValues.TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(ContentValues.TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(ContentValues.TAG, "onDestroy")
    }
}