package com.example.kotlinbasics

/*
data class CoffeeDetails(
    val sugarCount : Int,
    val name : String,
    val size: String,
    val creamAmount: Int
)
*/

fun main(){
  val shoppingList = mutableListOf("Processor", "RAM", "Graphics Card RTX 3060", "SSD")

  shoppingList.add("Cooling System")
  shoppingList.remove("Graphics Card RTX 3060")
  shoppingList.add("Graphics Card RTX 4090")

  for(index in 0 until shoppingList.size){
    println("item ${shoppingList[index]} is at index $index")
  }
}

/*

fun makeCoffee(coffeeDetails: CoffeeDetails){
  if(coffeeDetails.sugarCount == 0){
     println("Coffee no sugar for ${coffeeDetails.name} with ${coffeeDetails.creamAmount} cream")
  }else if (coffeeDetails.sugarCount == 1) {
      println("Coffee with ${coffeeDetails.sugarCount} spoon of sugar for ${coffeeDetails.name} with ${coffeeDetails.creamAmount} cream")
  }else{
     println("Coffee with ${coffeeDetails.sugarCount} spoons of sugar for ${coffeeDetails.name} with ${coffeeDetails.creamAmount}")
  }
}

*/
