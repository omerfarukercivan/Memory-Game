package com.example.memorygame

class Kayit {
    var id: Int = 0
    var zorluk: String = ""
    var sure: String = ""
    var skor: String = ""
    var hamleSayisi: String = ""

    constructor(zorluk:String, sure:String, skor:String, hamleSayisi:String){
        this.zorluk = zorluk
        this.sure = sure
        this.skor = skor
        this.hamleSayisi = hamleSayisi
    }
    constructor(){

    }
}
