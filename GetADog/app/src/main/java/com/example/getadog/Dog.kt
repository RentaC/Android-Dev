package com.example.getadog
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Dog : RealmObject() {
    @PrimaryKey
    var id: String = ""
    var breed: String = ""
    var link: String = ""
}