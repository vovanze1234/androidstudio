package com.example.rumirealafanovlesson4

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.example.rumirealafanovlesson4.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false
    private var binding: ActivityPlayerBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Создаем объект привязки данных с помощью метода inflate и устанавливаем его как контент активности
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        // Имитация загрузки трека
        loadMedia()

        // Обработчик нажатия на кнопку воспроизведения/паузы
        binding!!.buttonPlayPause.setOnClickListener {
            if (isPlaying) {
                pauseMedia()
            } else {
                playMedia()
            }
        }

        // Обработчик изменения позиции SeekBar
        binding!!.seekBarProgress.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                if (isPlaying) {
                    pauseMedia()
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                if (mediaPlayer != null && !isPlaying) {
                    playMedia()
                }
            }
        })

        // Обработчик нажатия на кнопку "Предыдущий трек"
        binding!!.buttonPrevious.setOnClickListener {
            pauseMedia()
            mediaPlayer?.seekTo(0)
            playMedia()
        }

        // Обработчик нажатия на кнопку "Следующий трек"
        binding!!.buttonNext.setOnClickListener { nextMedia() }
    }

    // Метод для загрузки трека и настройки SeekBar
    private fun loadMedia() {
        mediaPlayer = MediaPlayer.create(this, R.raw.videoplayback)
        mediaPlayer?.let {
            binding!!.seekBarProgress.max = it.duration
        }
    }

    // Метод для запуска воспроизведения трека
    private fun playMedia() {
        mediaPlayer?.start()
        isPlaying = true
        // Обновляем иконку кнопки в зависимости от состояния воспроизведения
        updatePlayPauseButton()
        // Запускаем обновление позиции SeekBar
        updateSeekBar()
    }

    // Метод для паузы воспроизведения трека
    private fun pauseMedia() {
        mediaPlayer?.pause()
        isPlaying = false
        // Обновляем иконку кнопки в зависимости от состояния воспроизведения
        updatePlayPauseButton()
    }

    // Метод для перехода к следующему треку
    private fun nextMedia() {
        mediaPlayer?.seekTo(0)
        mediaPlayer?.pause()
        isPlaying = false
        // Обновляем иконку кнопки в зависимости от состояния воспроизведения
        updatePlayPauseButton()
    }

    // Метод для обновления иконки кнопки воспроизведения/паузы
    private fun updatePlayPauseButton() {
        // Обновляем иконку кнопки в зависимости от состояния воспроизведения
    }

    private fun updateSeekBar() {
        if (mediaPlayer != null && isPlaying) {
            // Обновляем позицию SeekBar в соответствии с проигрываемым треком
            val updateSeekBar = Runnable { updateSeekBar() }
            binding!!.seekBarProgress.postDelayed(updateSeekBar, 1000)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
