package com.example.rumirealafanovlesson6

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editTextGroup: EditText
    private lateinit var editTextList: EditText
    private lateinit var editTextFilm: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        editTextGroup = findViewById(R.id.editTextGroup)
        editTextList = findViewById(R.id.editTextList)
        editTextFilm = findViewById(R.id.editTextFilm)

        loadSavedData()

        val buttonSave: Button = findViewById(R.id.button)
        buttonSave.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        val group = editTextGroup.text.toString()
        val list = editTextList.text.toString()
        val film = editTextFilm.text.toString()

        val editor = sharedPreferences.edit()
        editor.putString("group", group)
        editor.putString("list", list)
        editor.putString("film", film)
        editor.apply()
    }

    private fun loadSavedData() {
        val group = sharedPreferences.getString("group", "")
        val list = sharedPreferences.getString("list", "")
        val film = sharedPreferences.getString("film", "")

        editTextGroup.setText(group)
        editTextList.setText(list)
        editTextFilm.setText(film)
    }
}