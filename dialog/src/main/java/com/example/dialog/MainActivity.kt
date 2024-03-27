package com.example.dialog

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickShowDialog(view: View?) {
        val dialogFragment = MyDialogFragment()
        dialogFragment.show(supportFragmentManager, "mirea")
    }

    fun onOkClicked() {
        Toast.makeText(
            applicationContext, "Вы выбрали кнопку \"Иду дальше\"!",
            Toast.LENGTH_LONG
        ).show()
    }

    fun onCancelClicked() {
        Toast.makeText(
            applicationContext, "Вы выбрали кнопку \"Нет\"!",
            Toast.LENGTH_LONG
        ).show()
    }

    fun onNeutralClicked() {
        Toast.makeText(
            applicationContext, "Вы выбрали кнопку \"На паузе\"!",
            Toast.LENGTH_LONG
        ).show()
    }

    fun showSnackbar(view: View) {
        Snackbar.make(view, "Snackbar сообщение", Snackbar.LENGTH_SHORT).show()
    }

    fun showTimePickerDialog(view: View) {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _: TimePicker?, hourOfDay: Int, minute: Int ->
            },
            hour,
            minute,
            true
        )
        timePickerDialog.show()
    }

    fun showDatePickerDialog(view: View) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    fun showProgressDialog(view: View) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Загрузка...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        view.postDelayed({
            progressDialog.dismiss()
        }, 3000)
    }
}
