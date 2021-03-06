package com.example.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Chronometer
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.memorygame.R.drawable.*
import kotlinx.android.synthetic.main.activity_easy.hamleSayisi
import kotlinx.android.synthetic.main.activity_easy.img1
import kotlinx.android.synthetic.main.activity_easy.img10
import kotlinx.android.synthetic.main.activity_easy.img11
import kotlinx.android.synthetic.main.activity_easy.img12
import kotlinx.android.synthetic.main.activity_easy.img13
import kotlinx.android.synthetic.main.activity_easy.img14
import kotlinx.android.synthetic.main.activity_easy.img15
import kotlinx.android.synthetic.main.activity_easy.img16
import kotlinx.android.synthetic.main.activity_easy.img2
import kotlinx.android.synthetic.main.activity_easy.img3
import kotlinx.android.synthetic.main.activity_easy.img4
import kotlinx.android.synthetic.main.activity_easy.img5
import kotlinx.android.synthetic.main.activity_easy.img6
import kotlinx.android.synthetic.main.activity_easy.img7
import kotlinx.android.synthetic.main.activity_easy.img8
import kotlinx.android.synthetic.main.activity_easy.img9
import kotlinx.android.synthetic.main.activity_easy.skor
import kotlinx.android.synthetic.main.activity_zor.*
import android.media.MediaPlayer

var mediaPlayer: MediaPlayer? = null
var mediaPlayer1: MediaPlayer? = null

class Zor : AppCompatActivity() {
    private lateinit var buttons: List<ImageButton>
    private lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null

    private var clickCount = 0
    private var match = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zor)

        val images = mutableListOf(kar, ucak, capa, semsiye, dolar, ay,
                                   gunes, kek, kamera, testere, bulut, cocuk,
                                   kalem, gemi, araba, yaprak, kalp, gamepad)
        images.addAll(images)
        images.shuffle()

        buttons = listOf(img1,  img2,  img3,  img4,  img5,  img6,
                         img7,  img8,  img9,  img10, img11, img12,
                         img13, img14, img15, img16, img17, img18,
                         img19, img20, img21, img22, img23, img24,
                         img25, img26, img27, img28, img29, img30,
                         img31, img32, img33, img34, img35, img36)

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
                button.alpha = 0.45f
            }
            button.setImageResource(if (card.isFaceUp) card.identifier else zz)
        }
    }

    private fun updateModels(position: Int) {
        val card = cards[position]
        if (card.isFaceUp) {
            Toast.makeText(this, "Ge??ersiz Hamle!", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(this, "E??le??tirildi!!", Toast.LENGTH_SHORT).show()
            match += 1
            score += 10
            mediaPlayer = MediaPlayer.create(this, R.raw.alert)
            mediaPlayer?.start()
            eslesmeSayisi.text = match.toString()
            cards[position1].isMatched = true
            cards[position2].isMatched = true

            if(match == 18){
                meter.stop()
                mediaPlayer1 = MediaPlayer.create(this, R.raw.mario)
                mediaPlayer1?.start()
                val mesaj = AlertDialog.Builder(this)
                mesaj.setTitle("TEBR??KLER!")
                    .setMessage("OYUNU KAZANDINIZ.")
                    .setPositiveButton("Ana Men??ye D??n"){dialogInterface, it ->
                        val intent = Intent(this,MainActivity::class.java)
                        this@Zor.finish()
                        startActivity(intent)
                    }
                    .setNegativeButton("Tekrar Oyna"){dialogInterface, it ->
                        val intent = Intent(this,SeviyeEkrani::class.java)
                        this@Zor.finish()
                        startActivity(intent)
                    }
                    .show()
                val context = this
                var db = dataBaseHelper(context)
                var kayit = Kayit("Zor   ", meter.text.toString(), (((skor.text.toString()).toInt())+10).toString(), hamleSayisi.text.toString())
                db.insertData(kayit)
            }
        }else{
            score -= 2
        }
        skor.text = score.toString()
    }
}