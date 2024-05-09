package com.example.systemintentsapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun onClickCall(view: View) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:89811112233")
        startActivity(intent)
    }

    fun onClickOpenBrowser(view: View) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://www.twitch.tv/cs2_paragon_ru")
        startActivity(intent)
    }

    fun onClickOpenMaps(view: View) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("geo:24,83827 55,404213")
        startActivity(intent)
    }
}