package com.ekodex.manpowerhrms

data class Client_Data(
    var id:String,
    var title:String
){
    override fun toString(): String = title // this is what AutoCompleteTextView will show
}

