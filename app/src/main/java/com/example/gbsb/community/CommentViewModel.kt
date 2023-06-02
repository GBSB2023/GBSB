package com.example.gbsb.community

import androidx.lifecycle.ViewModel

class CommentViewModel: ViewModel() {
    lateinit var boardid: String
    fun setBoardId(id : String){
        boardid=id
    }
    fun getBoardId(): String {
        return boardid
    }
}