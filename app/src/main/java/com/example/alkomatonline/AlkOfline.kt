package com.example.alkomatonline

import android.content.Intent
import android.graphics.Color
import android.graphics.Color.GREEN
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_alk_ofline.*
import java.math.BigDecimal
import java.math.RoundingMode

class AlkOfline : AppCompatActivity() {

    var isOkay:Boolean?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alk_ofline)

        var gender: Double = 0.7
        var clicked:Boolean=false
        buWoman.setOnClickListener {
            buWoman.setBackgroundColor(Color.GREEN)
            buMan.setBackgroundColor(Color.WHITE)
            gender = 0.6
            clicked=true
        }

        buMan.setOnClickListener {
            buMan.setBackgroundColor(Color.GREEN)
            buWoman.setBackgroundColor(Color.WHITE)
            gender = 0.7
            clicked=true
        }



            buScore.setOnClickListener {

                val weight = Integer.parseInt(weightTxt.text.toString())
                val sTime = startHour.text.toString()
                val fTime = finishHour.text.toString()

                val beerVolume = (beerVolume.text.toString()).toDouble()
                val beerPercent = (beerPercent.text.toString()).toDouble()
                val vodkaVolume = (vodkaVolume.text.toString()).toDouble()
                val vodkaPercent = (vodkaPercent.text.toString()).toDouble()
                val wineVolume = (wineVolume.text.toString()).toDouble()
                val winePercent = (winePercent.text.toString()).toDouble()
                val whiskyVolume = (whiskyVolume.text.toString()).toDouble()
                val whiskyPercent = (whiskyPercent.text.toString()).toDouble()
                val othersVolume = (othersVolume.text.toString()).toDouble()
                val othersPercent = (othersPercent.text.toString()).toDouble()


                isOkay=IsOkay(clicked,weight,sTime,fTime)


                if(isOkay==true) {
                    var sHour = sTime.take(2).toInt()
                    var sMinute = sTime.takeLast(2).toInt()
                    var fHour = fTime.take(2).toInt()
                    var fMinute = fTime.takeLast(2).toInt()

                    val pureAlcohol = beerVolume * 0.01 * beerPercent * 0.8 + vodkaVolume * 0.01 * vodkaPercent * 0.8 + whiskyVolume * 0.01 * whiskyPercent * 0.8 + wineVolume * 0.01 * winePercent * 0.8 + othersVolume * 0.01 * othersPercent * 0.8


                    if (sMinute > 30) {
                        sHour++
                    }

                    if (fMinute > 30) {
                        fHour++
                    }

                    var drinkingTime: Int

                    if (fHour < sHour) {
                        drinkingTime = 24 - sHour + fHour
                    } else {
                        drinkingTime = fHour - sHour
                    }

                    var promiles = pureAlcohol / (gender * weight) - 0.1 * drinkingTime

                    if(promiles<0){promiles=0.0}

                    var maxPromiles = promiles

                    val roundPromiles = BigDecimal(maxPromiles).setScale(2, RoundingMode.HALF_EVEN).toDouble()
                    val roundPureAlcohol = BigDecimal(pureAlcohol).setScale(1, RoundingMode.HALF_EVEN).toDouble()

                    var fMin = "00"
                    do {
                        if (fMin == "00") {
                            fMin = "30"
                        } else {
                            fMin = "00"
                            fHour = fHour.plus(1)
                        }
                        promiles = promiles.minus(0.07)

                        if (fHour == 24) {
                            fHour = 0
                        }
                    } while (promiles > 0.05)

                    val sobriety: String = "$fHour:$fMin"



                    var offlineResult: Intent = Intent(applicationContext, ResultOfDrinking::class.java)

                    offlineResult.putExtra("PROMILES", roundPromiles)
                    offlineResult.putExtra("SOBRIETY", sobriety)
                    offlineResult.putExtra("PUREALCOHOL", roundPureAlcohol)
                    startActivity(offlineResult)
                }
            }


    }

    fun IsOkay(clicked:Boolean, weight:Int,sTime:String,fTime:String):Boolean{
       var flag:Boolean=true

        if(!clicked){
            Toast.makeText(this,"Nie wybrano płci!", Toast.LENGTH_SHORT).show()
            flag=false
        }

        if (weight in 251..-1){
            Toast.makeText(this,"Niepoprawna waga!", Toast.LENGTH_SHORT).show()
            flag=false
        }

        if(sTime.length !=5){
            Toast.makeText(this,"Niepoprawna godzina startu!", Toast.LENGTH_SHORT).show()
            Toast.makeText(this,"Prawidłowy format: 02:20", Toast.LENGTH_SHORT).show()
            flag=false
        }

        if(fTime.length !=5){
            Toast.makeText(this,"Niepoprawna godzina zakończenia!", Toast.LENGTH_SHORT).show()
            Toast.makeText(this,"Prawidłowy format: 02:20", Toast.LENGTH_SHORT).show()
            flag=false
        }


        return flag
    }






}