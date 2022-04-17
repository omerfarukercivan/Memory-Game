package com.example.memorygame

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val database_name = "Skorlar"
val table_name = "Skor"
val col_id = "id"
val col_zorluk = "zorluk"
val col_skor = "skor"
val col_sure = "sure"
val col_hamleSayisi = "hamleSayisi"

class dataBaseHelper(var context: Context):SQLiteOpenHelper(context,
database_name,null,1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        var createTable = " CREATE TABLE "+ table_name+"("+
                col_id +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                col_zorluk +" VARCHAR(50),"+
                col_skor +" VARCHAR(50),"+
                col_sure +" VARCHAR(50),"+
                col_hamleSayisi +" VARCHAR(50))"
        p0?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(kayit: Kayit){
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(col_hamleSayisi, kayit.hamleSayisi)
        cv.put(col_skor, kayit.skor)
        cv.put(col_sure, kayit.sure)
        cv.put(col_zorluk, kayit.zorluk)

        var sonuc = db.insert(table_name,null,cv)
        if(sonuc == (-1).toLong()){
            Toast.makeText(context, "KAYIT BAŞARISIZ", Toast.LENGTH_LONG).show()
        }
    }

    fun readData(): MutableList<Kayit> {
        var liste: MutableList<Kayit> = ArrayList()
        val db = this.readableDatabase
        var sorgu = "Select * from "+ table_name + " ORDER BY skor DESC "
        var sonuc = db.rawQuery(sorgu, null)
        if(sonuc.moveToFirst()){
            do {
                var kayit = Kayit()
                kayit.id = sonuc.getString(sonuc.getColumnIndex(col_id)).toInt()
                kayit.zorluk = sonuc.getString(sonuc.getColumnIndex(col_zorluk))
                kayit.sure = sonuc.getString(sonuc.getColumnIndex(col_sure))
                kayit.skor = sonuc.getString(sonuc.getColumnIndex(col_skor))
                kayit.hamleSayisi = sonuc.getString(sonuc.getColumnIndex(col_hamleSayisi))
                liste.add(kayit)
            }while (sonuc.moveToNext())
        }
        sonuc.close()
        db.close()
        return liste
    }
}


