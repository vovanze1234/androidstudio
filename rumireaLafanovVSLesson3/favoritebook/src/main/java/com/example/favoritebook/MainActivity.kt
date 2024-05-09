package com.example.favoritebook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textViewBookTitle: TextView = findViewById(R.id.textViewBookTitle)
        val buttonOpenInputScreen: Button = findViewById(R.id.buttonOpenInputScreen)

        textViewBookTitle.text = "Тут появится название вашей любимой книги!"

        buttonOpenInputScreen.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivityForResult(intent, REQUEST_CODE_INPUT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_INPUT && resultCode == RESULT_OK) {
            val bookTitle = data?.getStringExtra("bookTitle")
            bookTitle?.let {
                val textViewBookTitle: TextView = findViewById(R.id.textViewBookTitle)
                textViewBookTitle.text = "Название Вашей любимой книги: $bookTitle"
            }
        }
    }

    companion object {
        const val REQUEST_CODE_INPUT = 1
    }
}