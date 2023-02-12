package com.uxapp.oktava.viewmodels.songs

class SongChooseProcess {
    private var chosenSongId = -1
    fun choose(id: Int){
        chosenSongId = id
    }
    fun isChosen(): Boolean = chosenSongId != -1
    fun getChosenSongId(): Int{
        return chosenSongId
    }
    fun notChosen(){
        chosenSongId = -1
    }
}