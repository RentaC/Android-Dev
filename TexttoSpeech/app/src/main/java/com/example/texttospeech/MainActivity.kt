package com.example.texttospeech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.github.mfathi91.number2words.NumberToWords
//import com.toyama.includes.number2word.NumberToWordConverter

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val numbers = generateNumberList() // Generate a list of numbers from Zero to One Thousand
        adapter = CustomAdapter(numbers, this) // Pass the context as a parameter
        recyclerView.adapter = adapter
    }

    private fun generateNumberList(): List<String> {
        val numbers = mutableListOf<String>()
        for (i in 0..1000) {
            numbers.add(convertNumberToText(i))
        }
        return numbers
    }

    private fun convertNumberToText(number: Int): String {
        if (number == 0) {
            return "Zero"
        }

        val units = arrayOf(
            "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
            "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen",
            "Eighteen", "Nineteen"
        )
        val tens = arrayOf("", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety")

        var numberText = ""
        if (number < 20) {
            numberText = units[number]
        } else if (number < 100) {
            numberText = tens[number / 10] + " " + units[number % 10]
        } else if (number < 1000) {
            if ((number % 100) != 0) {
                numberText = units[number / 100] + " Hundred " + convertNumberToText(number % 100)
            } else { numberText = units[number / 100] + " Hundred " }
        } else if (number == 1000) {
            numberText = " One Thousand "
        }
//        } else if (number < 1000000) {
//            numberText = convertNumberToText(number / 1000) + " Thousand " + convertNumberToText(number % 1000)
//        }

        return numberText.trim()
    }

//    private fun generateNumberList(): List<String> {
//        val numbers = mutableListOf<String>()
//        for (i in 0..1000) {
//            numbers.add(NumberUtil.convertToText(i)) // Use a utility function to convert numbers to text (e.g., Zero, One, Two, etc.)
//        }
//        return numbers
//    }

//    private fun generateNumberList(): List<String> {
//        val numbers = mutableListOf<String>()
//        val numberToWords = NumberToWords()
//        for (i in 0..1000) {
//            numbers.add(numberToWords.convert(i))
//        }
//        return numbers
//    }

}
