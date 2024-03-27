package com.example.activitylifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var textView: TextView? = null

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById<TextView>(R.id.textView)
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
        textView?.text = "onStart"
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
        textView?.text = "onResume"
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
        textView?.text = "onPause"
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
        textView?.text = "onStop"
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
        textView?.text = "onDestroy"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState")
        textView?.text = "onSaveInstanceState"
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(TAG, "onRestoreInstanceState")
        textView?.text = "onRestoreInstanceState"
    }
}
