package com.example.mydogapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private val retrofit = Retrofit.Builder()
    .baseUrl("https://dog.ceo/api/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val breedService: DogApi = retrofit.create(DogApi::class.java)

interface DogApi {

    // Fetch list of all breeds
    @GET("breeds/list/all")
    suspend fun getBreedsList(): BreedsResponse

    // Fetch random image for a specific breed
    @GET("breed/{breed}/images/random")
    suspend fun getRandomImageForBreed(@Path("breed") breed: String): BreedImageResponse

}