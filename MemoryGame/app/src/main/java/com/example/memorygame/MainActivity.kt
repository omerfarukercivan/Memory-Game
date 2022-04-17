package com.example.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buton = findViewById<Button>(R.id.btnBasla)
        buton.setOnClickListener {
            val intent = Intent(this,SeviyeEkrani::class.java)
            this@MainActivity.finish()
            startActivity(intent)
        }

        val buton1 = findViewById<Button>(R.id.btnCikis)
        buton1.setOnClickListener {
            this@MainActivity.finish()
            exitProcess(0)
        }

        val context = this
        var db = dataBaseHelper(context)

        var data = db.readData()
        tvsonuc.text = ""

        for (i in 0 until data.size){
            tvsonuc.append(data.get(i).zorluk+ "                         " +data.get(i).sure+ "                    "
            +data.get(i).skor+ "                           " +data.get(i).hamleSayisi +"\n")
        }
    }
}