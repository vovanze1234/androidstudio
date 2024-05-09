package com.example.thread

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.thread.databinding.ActivityMainBinding

import java.util.Arrays

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val infoTextView: TextView = binding?.textView ?: return
        val mainThread = Thread.currentThread()
        infoTextView.text = "Имя текущего потока: ${mainThread.name}"
        mainThread.name = "МОЙ НОМЕР ГРУППЫ: 11, НОМЕР ПО СПИСКУ: 100, МОЙ ЛЮБИИМЫЙ ФИЛЬМ:Живая сталь"
        infoTextView.append("\nНовое имя потока: ${mainThread.name}")
        Log.d(MainActivity::class.java.simpleName, "Stack:	${Arrays.toString(mainThread.stackTrace)}")
        Log.d(MainActivity::class.java.simpleName, "Group:	${mainThread.threadGroup}")
        binding?.buttonMirea?.setOnClickListener {
            calculateAveragePairs()
            Thread {
                val numberThread = counter++
                Log.d(
                    "ThreadProject",
                    String.format(
                        "Запущен	поток	№	%d	студентом	группы	№	%s	номер	по списку	№	%d	",
                        numberThread,
                        "БСБО-11-21",
                        -100
                    )
                )
                val endTime = System.currentTimeMillis() + 20 * 1000
                while (System.currentTimeMillis() < endTime) {
                    synchronized(this) {
                        try {
                            (endTime - System.currentTimeMillis())
                            /*Log.d(
                                MainActivity::class.java.simpleName,
                                "Endtime:	$endTime"
                            )*/
                        } catch (e: Exception) {
                            throw RuntimeException(e)
                        }
                    }
                    // Log.d("ThreadProject", "Выполнен поток №	$numberThread")
                }
            }.start()
        }
    }

    private fun calculateAveragePairs() {
        val totalPairs: Int = binding?.editTextTotalPairs?.text.toString().toIntOrNull() ?: return
        val totalDays: Int = binding?.editTextTotalDays?.text.toString().toIntOrNull() ?: return

        Thread {
            val result = totalPairs.toDouble() / totalDays
            Handler(Looper.getMainLooper()).post {
                binding?.textView?.append("\n")
                binding?.textView?.append(
                    String.format(
                        "Среднее количество пар в день за месяц: %.2f",
                        result
                    )
                )
            }
        }.start()
    }
}
