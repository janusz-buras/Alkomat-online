package com.example.alkomatonline

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_alk_online.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round


class AlkOnline : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alk_online)
        var id= SizeOfData("%")

        var volume:Double
        var percent:Double
        var constant:Double=CheckInfo(id, "Gender").toDouble()
        var weight:Double=CheckInfo(id, "Weight").toDouble()

        buBeer.setOnClickListener {
        volume=(brVolume.text.toString()).toDouble()
            percent=(brPercent.text.toString()).toDouble()
            DrinkAlcohol(volume,percent,weight,constant,id)
            Toast.makeText(this, "Wypito piwko!", Toast.LENGTH_SHORT).show()
        }

        buVodka.setOnClickListener {
            volume=vaVolume.text.toString().toDouble()
            percent=vaPercent.text.toString().toDouble()
            DrinkAlcohol(volume,percent,weight,constant,id)
            Toast.makeText(this, "Wypito wódeczkę!", Toast.LENGTH_SHORT).show()
        }

        buWine.setOnClickListener {
            volume=weVolume.text.toString().toDouble()
            percent=wePercent.text.toString().toDouble()
            DrinkAlcohol(volume,percent,weight,constant,id)
            Toast.makeText(this, "Wypito winko!", Toast.LENGTH_SHORT).show()
        }

        buWhisky.setOnClickListener {
            volume=wyVolume.text.toString().toDouble()
            percent=wyPercent.text.toString().toDouble()
            DrinkAlcohol(volume,percent,weight,constant,id)
            Toast.makeText(this, "Wypito whisky!", Toast.LENGTH_SHORT).show()
        }

        buOthers.setOnClickListener {
            volume=osVolume.text.toString().toDouble()
            percent=osPercent.text.toString().toDouble()
            DrinkAlcohol(volume,percent,weight,constant,id)
            Toast.makeText(this, "Wypito coś tajemniczego!", Toast.LENGTH_SHORT).show()
        }


        buCount.setOnClickListener {

            val maxPromiles=CheckInfo(id,"Promiles").toDouble()
            val sobriety=CheckInfo(id,"Sobriety")
            val pureAlcohol=CheckInfo(id,"PureAlcohol").toDouble()
            val timeF = SimpleDateFormat("HH:mm").format(Calendar.getInstance().time)
            var hour=  timeF.take(2).toInt()
            var min = timeF.takeLast(2).toInt()

            if(min>30){hour++}

            var result: Intent = Intent(applicationContext, ResultOfDrinking::class.java)

            result.putExtra("PROMILES", maxPromiles)
            result.putExtra("SOBRIETY", sobriety)
            result.putExtra("PUREALCOHOL", pureAlcohol)
            result.putExtra("HOUR", hour)
            startActivity(result)

        }


        buFinish.setOnClickListener {
           PartyFinish(id)
        }


    }


    fun DrinkAlcohol(volume: Double, percentage:Double, weight:Double, constant: Double,id: Int){



        val actualTime = SimpleDateFormat("HH:mm").format(Calendar.getInstance().time)
        val startTime=CheckInfo(id,"StartDate")
        var startHour=startTime.substring(11,13).toInt()
        var startMin= startTime.substring(14,16).toInt()
        var day=startTime.substring(0,2).toInt()
       var month=startTime.substring(3,5).toInt()
        var year= startTime.substring(6,10).toInt()
       var actualHour=  actualTime.take(2).toInt()
        var actualMin = actualTime.takeLast(2).toInt()



        if (actualMin>30){ actualHour++ }
        val aHour=actualHour


        if (startMin>30){ startHour++ }

        var drinkingDuration=actualHour-startHour

        val newPureAlcohol = volume * 0.01 *percentage * 0.8

        var pureAlcohol=CheckInfo(id, "PureAlcohol").toDouble()
        pureAlcohol += newPureAlcohol

        var promiles=pureAlcohol/(constant*weight)-0.1*drinkingDuration
        if(promiles<0){
            promiles=0.0
        }

      val roundPromiles=BigDecimal(promiles).setScale(2, RoundingMode.HALF_EVEN).toDouble()
        val roundPureAlcohol=BigDecimal(pureAlcohol).setScale(1, RoundingMode.HALF_EVEN).toDouble()


        var min="00"
        do {
            if(min=="00"){
                min="30"
            }
            else{
                min="00"
                actualHour= actualHour.plus(1)
            }
            promiles= promiles.minus(0.07)

            if(actualHour==24){
                actualHour=0
                day++
                if(day>31){
                    month++
                }

            }
        }while(promiles>0.05)





        var correctedHour:String
        var correctedDay:String
        var correctedMonth:String

        if(actualHour<=9){
             correctedHour="0$actualHour"
        }else{
            correctedHour="$actualHour"
        }

        if(day<=9){
            correctedDay="0$day"
        }else{
            correctedDay="$day"
        }

        if(month<=9){
            correctedMonth="0$month"
        }else{
            correctedMonth="$month"
        }

        val sobrietyTime = "$correctedHour:$min $correctedDay.$correctedMonth.$year"


        var dbManager=DbManager(this)
        var values= ContentValues()
        values.put("PureAlcohol",roundPureAlcohol)
        values.put("Promiles",roundPromiles)
        values.put("Sobriety",sobrietyTime)
        var selectionArs= arrayOf(id.toString())
        val ID = dbManager.Update(values,"ID=?",selectionArs)

       // var promiles=pureAlcohol/(constant*weight)-0.1*drinkingTime

    }

    fun CheckInfo(id:Int, columnName:String):String{
        val dbManager=DbManager(this)
        val cursor = dbManager.getRaw(id)
        cursor!!.moveToLast()
        val info:String=cursor.getString(cursor.getColumnIndex(columnName))
        return  info
    }


    fun SizeOfData(title:String):Int{
        var dbManager=DbManager(this)
        val projections=arrayOf("ID","Title","StartDate","PureAlcohol","Promiles","FinishDate","IsFinish")
        val selectionArgs = arrayOf(title)
        val cursor=dbManager.Query(projections,"Title like ?", selectionArgs, "Title")
        var size=0
        if(cursor.moveToFirst()){
            do{
             size++
            }while(cursor.moveToNext())
        }
       return size

    }


    fun  PartyFinish(id:Int){
        var dbManager=DbManager(this)


        val finishDate = SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Calendar.getInstance().time)


        var values= ContentValues()
        values.put("IsFinish","true")
        values.put("FinishDate",finishDate)
            var selectionArs= arrayOf(id.toString())
            val ID = dbManager.Update(values,"ID=?",selectionArs)

                Toast.makeText(this, "Zakończono imprezę", Toast.LENGTH_LONG).show()
            finish()

        }

        }

