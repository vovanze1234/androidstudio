package com.example.toastapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editText = findViewById<EditText>(R.id.editText)
        val buttonCount = findViewById<Button>(R.id.buttonCount)

        buttonCount.setOnClickListener {
            val text = editText.text.toString()
            val count = text.length
            val message = "Лафанов БСБО-11-21 Количество символов - $count"
            showToast(message)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}