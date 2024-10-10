package com.example.kotlinbasics

class Dog (val name: String, val breed: String, var age: Int = 0 ) {

    init{
        bark()
    }

    fun bark(){
        println("Woof Woof")
    }
}