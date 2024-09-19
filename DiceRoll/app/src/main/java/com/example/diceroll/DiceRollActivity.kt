package com.example.diceroll

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class DiceRollActivity : AppCompatActivity() {

    private lateinit var diceImageView: ImageView
    private lateinit var diceImageView2: ImageView
    private lateinit var rollButton: Button

    // Dice Images Array
    private val diceImages = arrayOf(
        R.drawable.dice1,
        R.drawable.dice2,
        R.drawable.dice3,
        R.drawable.dice4,
        R.drawable.dice5,
        R.drawable.dice6
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dice_roll)

        diceImageView = findViewById(R.id.diceImageView)
        diceImageView2 = findViewById(R.id.diceImageView2)
        rollButton = findViewById(R.id.rollButton)

        rollButton.setOnClickListener {
            rollDice()
        }
    }

    private fun rollDice() {
        // Random choice of dices
        val random = Random()
        val diceIndex = random.nextInt(6)
        val diceIndex2 = random.nextInt(6)

        // Define images on ImageView
        diceImageView.setImageResource(diceImages[diceIndex])
        diceImageView2.setImageResource(diceImages[diceIndex2])
    }
}
