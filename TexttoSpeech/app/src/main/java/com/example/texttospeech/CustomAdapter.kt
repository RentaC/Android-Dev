package com.example.texttospeech

import android.content.Context
import android.media.MediaPlayer
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class CustomAdapter(private val numbers: List<String>, private val context: Context) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    private lateinit var tts: TextToSpeech

    init {
        // Initialize TextToSpeech
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts.language = Locale.US // Set desired language (e.g., US English)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_number, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val number = numbers[position]

        holder.numberButton.text = number
        holder.numberButton.contentDescription = "Button for $number" // Set the content description dynamically

        holder.numberButton.setOnClickListener {

            // Use Text-to-Speech for number pronunciation
            tts.speak(number, TextToSpeech.QUEUE_FLUSH, null, null)

// Play Custom Sound
//            val soundId = getSoundIdForNumber(number, holder.itemView.context)
//            playSound(soundId, holder.itemView.context)

        }
    }

    override fun getItemCount(): Int {
        return numbers.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numberButton: Button = itemView.findViewById(R.id.numberButton)
    }

// code related to playing a custom sound file

//    private fun getSoundIdForNumber(number: String, context: Context): Int {
//        // Implement the logic to retrieve the sound resource ID based on the number
//        // For example:
//        return context.resources.getIdentifier("sound_${number.toLowerCase()}", "raw", context.packageName)
//    }
//
//
//    private fun playSound(soundId: Int, context: Context) {
//        val mediaPlayer = MediaPlayer.create(context, soundId)
//        mediaPlayer.start()
//        mediaPlayer.setOnCompletionListener {
//            mediaPlayer.release()
//        }
//    }

}
