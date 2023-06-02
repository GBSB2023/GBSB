package com.example.gbsb.login

data class Account(
    var pId:String,
    var pPw: String,
    var pName: String,
    var pNickname: String,
    var uId: String
){
    constructor(): this("","","","","")
}