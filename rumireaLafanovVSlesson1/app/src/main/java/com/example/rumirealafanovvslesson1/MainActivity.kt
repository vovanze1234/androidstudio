package com.example.rumirealafanovvslesson1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Button
import android.widget.Toast
import android.view.View
import android.widget.CheckBox

class MainActivity : AppCompatActivity() {
    private var checkBox: CheckBox? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myTextView: TextView? = findViewById(R.id.textView)
        myTextView?.text = "Привет студент нашего вуза"

        val buttonWhoIAm: Button? = findViewById(R.id.whoiam)
        buttonWhoIAm?.setOnClickListener {
            myTextView?.text = "Вован"
            checkBox!!?.toggle()
        }
        checkBox = findViewById(R.id.checkBox)
    }

    fun onMyButtonClick(view: View) {
            Toast.makeText(this, "Второй способ!", Toast.LENGTH_SHORT).show()
            checkBox!!?.toggle()
    }
}