package com.example.getadog

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import coil.Coil
import coil.ImageLoader
import coil.load


class DogDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_dog_details)

        // Get the breed and image URL from the intent extras
        val breed = intent.getStringExtra("breed")
        val imageUrl = intent.getStringExtra("imageUrl")

        val titleTextView: TextView = findViewById(R.id.titleTextView)
        val dogImageView: ImageView = findViewById(R.id.dogImageView)

        // Set the breed as the title
        titleTextView.text = breed

        // Load the dog image using Coil
        dogImageView.load(imageUrl)
    }
}