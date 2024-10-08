package com.example.getadog

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.RealmConfiguration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private lateinit var dogAdapter: DogAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Realm.init(this) //initialize Realm

        recyclerView = findViewById(R.id.recyclerView)
        dogAdapter = DogAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = dogAdapter

        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            getDogImage()
        }

        dogAdapter.setOnItemClickListener { dog ->
            onItemClick(dog)
        }

        displaySavedDogs()
    }

    private fun getDogImage() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dog.ceo/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val dogApi = retrofit.create(DogApi::class.java)
        val call = dogApi.getRandomDog()

        call.enqueue(object : Callback<RandomDogResponse> {
            override fun onResponse(call: Call<RandomDogResponse>, response: Response<RandomDogResponse>) {
                if (response.isSuccessful) {
                    val dogImageUrl = response.body()?.message
                    if (dogImageUrl != null) {
                        saveDogToDatabase(dogImageUrl)
                    }
                }
            }

            override fun onFailure(call: Call<RandomDogResponse>, t: Throwable) {
                // Handle failure
                Log.e(TAG, "API call failed: ${t.message}")
            }
        })
    }

    private fun saveDogToDatabase(dogImageUrl: String) {
        val realmConfig = RealmConfiguration.Builder()
            .allowWritesOnUiThread(true) // Enable transactions on the UI thread
            .build()

        Realm.setDefaultConfiguration(realmConfig) // Set as default configuration

        val realm = Realm.getDefaultInstance()
        realm.executeTransactionAsync({ realm ->
            val dog = realm.createObject(Dog::class.java, UUID.randomUUID().toString())
            dog.breed = extractBreedFromImageUrl(dogImageUrl)
            dog.link = dogImageUrl
        }, {
            // Transaction succeeded
            realm.close() // Close the Realm instance inside the onSuccess callback
            displaySavedDogs()
        }, { error ->
            // Transaction failed
            Log.e(TAG, "Failed to save dog to database: ${error.message}")
            realm.close() // Close the Realm instance in case of an error
        })
        displaySavedDogs()
    }

    private fun displaySavedDogs() {
        val realmConfig = RealmConfiguration.Builder()
            .allowWritesOnUiThread(true) // Enable transactions on the UI thread
            .build()

        Realm.getInstanceAsync(realmConfig, object : Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                val dogs = realm.where(Dog::class.java).findAll()
                dogAdapter.updateData(dogs)
                //realm.close()
            }

            override fun onError(exception: Throwable) {
                Log.e(TAG, "Failed to open Realm instance: ${exception.message}")
            }
        })
    }

    private fun extractBreedFromImageUrl(imageUrl: String): String {
        // Extract the breed from the image URL
        val pattern = Regex("""https://images.dog.ceo/breeds/(.*)/.*""")
        val matchResult = pattern.find(imageUrl)
        return matchResult?.groupValues?.get(1) ?: ""
    }

    private fun onItemClick(dog: Dog) {
        val intent = Intent(this,  DogDetailsActivity::class.java)
        intent.putExtra("breed", dog.breed)
        intent.putExtra("imageUrl", dog.link)
        startActivity(intent)
    }
}
