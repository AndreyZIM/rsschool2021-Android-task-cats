package com.example.cats.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.example.cats.R
import com.example.cats.databinding.ActivityDetailBinding
import com.example.cats.model.Cat

class DetailActivity: AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater).also {setContentView(it.root)}

        val item = intent.extras?.get(INTENT_PARAM_ID)

        Glide.with(this)
            .load(item)
            .centerCrop()
            .error(R.drawable.ic_baseline_error_24)
            .into(binding.imageDetail)

        binding.buttonDownload.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                saveImageToGallery((binding.imageDetail.drawable as BitmapDrawable).bitmap)
            else
                requestPermission()
        }
        binding.buttonBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun requestPermission() {
        val permissionArray = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ActivityCompat.requestPermissions(this, permissionArray, REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                saveImageToGallery((binding.imageDetail.drawable as BitmapDrawable).bitmap)
            else
                Toast.makeText(this, "Please grant permission to write external storage.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveImageToGallery(bitmap: Bitmap) {
        MediaStore.Images.Media.insertImage(contentResolver, bitmap, "Cat", "Image of Cat")
        Toast.makeText(this, "Picture Saved.", Toast.LENGTH_SHORT).show()
    }



    companion object {
        const val INTENT_PARAM_ID = "id"
        const val INTENT_PARAM_IMAGE = "image"
        const val REQUEST_CODE = 123
    }

}