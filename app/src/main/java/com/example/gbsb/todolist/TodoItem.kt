package com.example.gbsb.todolist

import java.time.LocalDateTime

data class TodoItem(
    var id : String,
    var content:String,
    var timeStamp : String,
    var isDone:Boolean){

    constructor():this("","default Content", "yyyy-mm-dd HH:MM",false)
}