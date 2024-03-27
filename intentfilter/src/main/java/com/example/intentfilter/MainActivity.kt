package com.example.intentfilter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonShareStudentInfo = findViewById<Button>(R.id.buttonShareStudentInfo)

        buttonShareStudentInfo.setOnClickListener {
            shareStudentInfo()
        }
    }

    private fun shareStudentInfo() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "MIREA")
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Лафанов Владимир\nМИРЭА")
        startActivity(Intent.createChooser(shareIntent, "Лафанов Владимир Сергеевич"))
    }

    fun openMireaWebsite(view: android.view.View) {
        val address = Uri.parse("https://www.mirea.ru/")
        val openLinkIntent = Intent(Intent.ACTION_VIEW, address)
        startActivity(openLinkIntent)
    }
}
