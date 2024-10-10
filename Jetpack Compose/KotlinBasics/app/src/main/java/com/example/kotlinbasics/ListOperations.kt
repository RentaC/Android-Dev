package com.example.kotlinbasics

fun main(){
    val fruitList = mutableListOf("Apple", "Banana", "Pear", "Grape", "Mango")
    fruitList.add("Watermelon")
    fruitList.remove("Pear")
    if(fruitList.contains("Srtrfadf")){
        println("We do have Mango in the List")
    }else{
        println("We don't have Mango in the List")
    }
}