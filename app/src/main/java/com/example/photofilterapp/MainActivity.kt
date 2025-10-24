package com.example.photofilterapp

import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.photofilterapp.databinding.ActivityMainBinding
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var originalBitmap: Bitmap? = null
    private var filteredBitmap: Bitmap? = null

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        uri?.let { loadBitmapFromUri(it) } ?: showToast("Фото не выбрано")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnChoose.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.btnSave.setOnClickListener {
            filteredBitmap?.let {
                StorageHelper.saveBitmapToGallery(this, it)
                showToast("Фото сохранено!")
            } ?: showToast("Нет фото для сохранения")
        }

        setupFilterButtons()
    }

    private fun setupFilterButtons() = with(binding) {
        btnOriginal.setOnClickListener { originalBitmap?.let { setFiltered(it) } }
        btnGray.setOnClickListener { applyFilter(ImageProcessor::applyGrayScale) }
        btnSepia.setOnClickListener { applyFilter(ImageProcessor::applySepia) }
        btnInvert.setOnClickListener { applyFilter(ImageProcessor::applyInvert) }
        btnContrast.setOnClickListener { applyFilter(ImageProcessor::applyHighContrast) }
        btnBlur.setOnClickListener { applyFilter(ImageProcessor::applyBlur) }
        btnBrightness.setOnClickListener { applyFilter(ImageProcessor::applyBrightness) }
        btnSaturate.setOnClickListener { applyFilter(ImageProcessor::applySaturate) }
        btnVintage.setOnClickListener { applyFilter(ImageProcessor::applyVintage) }
    }

    private fun applyFilter(filter: (Bitmap) -> Bitmap) {
        originalBitmap?.let {
            filteredBitmap = filter(it)
            binding.imageView.setImageBitmap(filteredBitmap)
        } ?: showToast("Сначала выбери фото")
    }

    private fun loadBitmapFromUri(uri: Uri) {
        try {
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            originalBitmap = bitmap
            filteredBitmap = bitmap
            binding.imageView.setImageBitmap(bitmap)
        } catch (e: Exception) {
            showToast("Ошибка загрузки изображения: ${e.message}")
        }
    }

    private fun setFiltered(bitmap: Bitmap) {
        filteredBitmap = bitmap
        binding.imageView.setImageBitmap(bitmap)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
