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

//        // Load the dog image using Glide
//        Glide.with(this).load(imageUrl).into(dogImageView)

        // Load the dog image using Coil
        dogImageView.load(imageUrl)
//        {
//            crossfade(true)
//            //placeholder(R.drawable.placeholder)
//            //error(R.drawable.error)
//        }

    }
}



//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import coil.load
//import com.example.getadog.databinding.FragmentDogDetailsBinding
//
//class DogDetailsFragment : Fragment() {
//
//    private var _binding: FragmentDogDetailsBinding? = null
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentDogDetailsBinding.inflate(inflater, container, false)
//        val view = binding.root
//
//        val breed = requireArguments().getString("breed")
//        val imageUrl = requireArguments().getString("imageUrl")
//
//        binding.titleTextView.text = breed
//        binding.dogImageView.load(imageUrl)
//
//        return view
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}