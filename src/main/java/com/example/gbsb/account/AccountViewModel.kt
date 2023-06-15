package com.example.gbsb.account

import androidx.lifecycle.ViewModel

class AccountViewModel: ViewModel() {
    lateinit var item: Info
    lateinit var uId: String
    fun setData(transitem: Info){
        item=transitem
    }
    fun getData(): Info {
        return item
    }


    fun setuId(uid:String){
        uId=uid
    }
    fun getuId():String{
        return uId
    }
}