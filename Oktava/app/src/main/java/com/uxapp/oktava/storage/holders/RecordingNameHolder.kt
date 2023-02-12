package com.uxapp.oktava.storage.holders

class RecordingNameHolder {
    private lateinit var name: String
    fun put(s: String){
        name = s
    }

    fun get(): String{
        return name
    }
}