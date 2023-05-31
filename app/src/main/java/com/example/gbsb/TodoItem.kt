package com.example.gbsb

data class TodoItem(
    var id : String,
    var content:String,
    var date:String,
    var time:String,
    var isDone:Boolean){

    constructor():this("","default Content", "23.06.01", "14:20",false)
}