package com.example.securesharedpreferences

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.securesharedpreferences.databinding.ActivityMainBinding
import java.io.IOException
import java.security.GeneralSecurityException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        try {
            val mainKey = MasterKeys.getOrCreate(keyGenParameterSpec)
            val securePreferences = EncryptedSharedPreferences.create(
                "secret_shared_pref",
                mainKey,
                applicationContext,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
            securePreferences.edit().putString("NAME", "Сергей Владимирович Шнуров (род. 13 апреля 1973, Ленинград, СССР) — российский рок-музыкант, поэт").apply()
            binding.textPoet.text = securePreferences.getString("NAME", "")
            binding.imagePoet.setImageResource(R.drawable.shnurov)
        } catch (e: IOException) {
            throw RuntimeException(e)
        } catch (e: GeneralSecurityException) {
            throw RuntimeException(e)
        }
    }
}