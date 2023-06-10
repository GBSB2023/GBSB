package com.example.gbsb.todolist

import java.io.Serializable

data class Schedule(
    var id : String,
    var content:String,
    var date : String, // "yyyy-mm-dd"
    var time : String, // "HH:MM"
    var done : Boolean) : Serializable {

    constructor():this("","default Content", "yyyy-mm-dd", "HH:MM",false)
}