package com.example.mydogapp

data class BreedWithImage(
    val breed: String,
    val imageUrl: String
)

data class BreedsResponse(
    val message: Map<String, List<String>>,
    val status: String
)

// Data class for Random Image Response
data class BreedImageResponse(
    val message: String,
    val status: String
)