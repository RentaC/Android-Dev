package com.example.getadog

import retrofit2.Call
import retrofit2.http.GET

interface DogApi {
    @GET("api/breeds/image/random")
    fun getRandomDog(): Call<RandomDogResponse>
}
