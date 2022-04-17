package com.example.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Chronometer
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.memorygame.R.drawable.*
import kotlinx.android.synthetic.main.activity_easy.*
import android.media.MediaPlayer

class Easy : AppCompatActivity() {
    private lateinit var buttons: List<ImageButton>
    private lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null
    private var clickCount = 0
    private var match = 0
    private var score = 0
    var mediaPlayer: MediaPlayer? = null
    var mediaPlayer1: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easy)
        val images = mutableListOf(aa, bb, cc, dd, ee, ff, gg, hh)
        images.addAll(images)
        images.shuffle()

        buttons = listOf(img1,  img2,  img3,  img4,
                         img5,  img6,  img7,  img8,
                         img9,  img10, img11, img12,
                         img13, img14, img15, img16)

        cards = buttons.indices.map { index ->
            MemoryCard(images[index])
        }
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                updateModels(index)
                updateViews()
            }
        }
    }

    private fun updateViews() {
        cards.forEachIndexed { index, card ->
            val button = buttons[index]
            if (card.isMatched) {
                button.alpha = 0.35f
            }
            button.setImageResource(if (card.isFaceUp) card.identifier else zz)
        }
    }

    private fun updateModels(position: Int) {
        val card = cards[position]

        if (card.isFaceUp) {
            Toast.makeText(this, "Geçersiz Hamle!", Toast.LENGTH_SHORT).show()
            return
        }

        if(!card.isFaceUp){
            clickCount += 1
            hamleSayisi.text = clickCount.toString()
        }

        if (indexOfSingleSelectedCard == null) {
            restoreCards()
            indexOfSingleSelectedCard = position
        } else {
            checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }
        card.isFaceUp = !card.isFaceUp
    }

    private fun restoreCards() {
        for (card in cards) {
            if (!card.isMatched) {
                card.isFaceUp = false
            }
        }
    }

    private fun checkForMatch(position1: Int, position2: Int) {
        var meter = findViewById<Chronometer>(R.id.kronometre)
        meter.start()
        if (cards[position1].identifier == cards[position2].identifier) {
            Toast.makeText(this, "Eşleştirildi!!", Toast.LENGTH_SHORT).show()
            match += 1
            score += 10
            mediaPlayer = MediaPlayer.create(this, R.raw.alert)
            mediaPlayer?.start()
            eslesmeSayisi.text = match.toString()
            cards[position1].isMatched = true
            cards[position2].isMatched = true

            if(match == 8){
                meter.stop()
                mediaPlayer1 = MediaPlayer.create(this, R.raw.mario)
                mediaPlayer1?.start()
                val mesaj = AlertDialog.Builder(this)
                mesaj.setTitle("TEBRİKLER!")
                    .setMessage("OYUNU KAZANDINIZ.")
                    .setPositiveButton("Ana Menüye Dön"){dialogInterface, it ->
                        val intent = Intent(this,MainActivity::class.java)
                        this@Easy.finish()
                        startActivity(intent)
                    }
                    .setNegativeButton("Tekrar Oyna"){dialogInterface, it ->
                        val intent = Intent(this,SeviyeEkrani::class.java)
                        this@Easy.finish()
                        startActivity(intent)
                    }
                    .show()

                val context = this
                var db = dataBaseHelper(context)

                var kayit = Kayit("Kolay", meter.text.toString(), skor.text.toString(), hamleSayisi.text.toString())
                db.insertData(kayit)
            }
        }else{
            score -= 2
        }
        skor.text = score.toString()
    }
}