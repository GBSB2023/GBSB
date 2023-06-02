package com.example.gbsb.community.board

data class Board(
    var name: String,
    var content: String,
    var date: String,
    var writer: String,
    var category: String,
    var like: Int,
    var comment: Int,
    var boardid: String,
    var uid: String
) {
    // 기본 생성자
    constructor() : this("", "", "", "", "", -1, -1, "", "")
}