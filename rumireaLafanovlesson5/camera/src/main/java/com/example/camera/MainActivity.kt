package com.example.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.camera.databinding.ActivityMainBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var imageUri: Uri? = null
    private var isWork = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val cameraPermissionStatus = ContextCompat.checkSelfPermission(this,
            Manifest.permission.CAMERA
        )
        val storagePermissionStatus = ContextCompat.checkSelfPermission(this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        if (cameraPermissionStatus == PackageManager.PERMISSION_GRANTED
            && storagePermissionStatus == PackageManager.PERMISSION_GRANTED) {
            isWork = true
        } else {
            requestPermissions(arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), REQUEST_CODE_PERMISSION)
        }

        val cameraActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                if (result.resultCode == RESULT_OK) {
                    binding.imageView.setImageURI(imageUri)
                }
            }

        binding.imageView.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (isWork) {
                try {
                    val photoFile = createImageFile()

                    val authorities = applicationContext.packageName + ".fileprovider"
                    imageUri = FileProvider.getUriForFile(this, authorities, photoFile)
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)

                    cameraActivityResultLauncher.launch(cameraIntent)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_PERMISSION -> {
                isWork = grantResults.isNotEmpty()
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
        val imageFileName = "IMAGE_" + timeStamp + "_"

        return File.createTempFile(
            imageFileName,
            ".jpg",
            getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )
    }

    private companion object {
        const val REQUEST_CODE_PERMISSION = 100
    }
}
