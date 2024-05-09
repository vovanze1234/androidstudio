package com.example.rumirealafanovlesson4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.rumirealafanovlesson4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.editTextMirea.setText("Мой номер по списку №___")
        binding.buttonMirea.setOnClickListener {
            Log.d(MainActivity::class.simpleName, "onClickListener")
        }
        binding.button2.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, PlayerActivity::class.java)
            startActivity(intent)
        })
    }
}
