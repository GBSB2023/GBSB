package com.example.gbsb.todolist

data class Schedule(
    var id : String,
    var content:String,
    var date : String, // "yyyy-mm-dd"
    var time : String, // "HH:MM"
    var isDone:Boolean){

    constructor():this("","default Content", "yyyy-mm-dd", "HH:MM",false)
}