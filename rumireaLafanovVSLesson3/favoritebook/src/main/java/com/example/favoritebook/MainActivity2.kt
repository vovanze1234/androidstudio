package com.example.favoritebook

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val editTextBookTitle: EditText = findViewById(R.id.editTextBookTitle)
        val buttonSend: Button = findViewById(R.id.buttonSend)

        buttonSend.setOnClickListener {
            val bookTitle = editTextBookTitle.text.toString()
            val intent = Intent()
            intent.putExtra("bookTitle", bookTitle)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}