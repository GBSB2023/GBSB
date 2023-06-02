package com.example.gbsb.account

data class Info(
    var email:String,
    var call:String,
    var birth:String,
    var major:String,
    var introduce:String
){
    constructor(): this("","","","","")
}
