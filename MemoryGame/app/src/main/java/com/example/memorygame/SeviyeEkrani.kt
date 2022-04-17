package com.example.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SeviyeEkrani : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seviye_ekrani)

        val buton1 = findViewById<Button>(R.id.btnKolay)
        buton1.setOnClickListener {
            val intent = Intent(this,Easy::class.java)
            this@SeviyeEkrani.finish()
            startActivity(intent)
        }

        val buton2 = findViewById<Button>(R.id.btnZor)
        buton2.setOnClickListener {
            val intent = Intent(this,Zor::class.java)
            this@SeviyeEkrani.finish()
            startActivity(intent)
        }
    }
}