package com.example.kotlinbasics

fun main(){

    var computerChoice = ""
    var playerChoice = ""

    println("Rock, Paper or Scissors? Enter your choice: ")

    playerChoice = readln().toLowerCase()

    while ((playerChoice != "rock") && (playerChoice != "paper") && (playerChoice != "scissors")){
        println("Please enter a valid choice (Rock, Paper or Scissors): ")
        playerChoice = readLine().toString().toLowerCase()
    }

    val randomNumber = (1..3).random()

    computerChoice = when (randomNumber) {
        1 -> {
            "rock"
        }
        2 -> {
            "paper"
        }
        else -> {
            "scissors"
        }
    }

    println(computerChoice)

    val winner = when{
        playerChoice == computerChoice -> "Tie"
        playerChoice == "rock" && computerChoice == "scissors" -> "Player Wins"
        playerChoice == "paper" && computerChoice == "rock" -> "Player Wins"
        playerChoice == "scissors" && computerChoice == "paper" -> "Player Wins"
        else -> "Computer Wins"
    }

    println(winner)
}