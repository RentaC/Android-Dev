package com.example.kotlinbasics

fun main(){

    println("Please enter a number: ")

   try{
       var inputString = readln()
       var inputNumber  = inputString.toInt()
       val multiplier = 5
       inputNumber *= multiplier

       println("Result of operation is: $inputNumber")

   } catch (e: NumberFormatException) {
       println("Please Enter a Valid Number")
   }

}