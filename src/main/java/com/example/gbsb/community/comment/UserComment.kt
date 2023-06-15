package com.example.gbsb.community.comment

data class UserComment(
    var writer: String,
    var content: String,
    var date: String,
    var like: Int,
    var uid: String,
    var commentid: String
) {
    // 기본 생성자
    constructor() : this("", "", "", -1, "", "")
}