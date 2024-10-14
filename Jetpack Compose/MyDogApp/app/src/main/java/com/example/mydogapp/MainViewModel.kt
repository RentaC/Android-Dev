package com.example.mydogapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _breedImagePairs = mutableStateOf(BreedState())
    val breedImagePairs: State<BreedState> = _breedImagePairs

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            try {

                // Step 1: Fetch the list of all breeds
                val response = breedService.getBreedsList()
                val breeds = response.message.keys.toList() // Get the list of breed names

                // Step 2: Fetch a random image for each breed and build a list of BreedWithImage
                val breedWithImages = breeds.map { breed ->
                    val imageResponse = breedService.getRandomImageForBreed(breed)
                    BreedWithImage(breed, imageResponse.message)
                }

                // Step 3: Update the state with the breed and image list
                _breedImagePairs.value = _breedImagePairs.value.copy(
                    loading = false,
                    list = breedWithImages, // Update with fetched breeds and images
                    error = null
                )
            } catch (e: Exception) {
                _breedImagePairs.value = _breedImagePairs.value.copy(
                    loading = false,
                    error = "ERROR Fetching Categories ${e.message}"
                )
            }
        }
    }
}

data class BreedState(
    val loading: Boolean = true,
    val list: List<BreedWithImage> = emptyList(), // Updated list to handle breeds with images
    val error: String? = null
)