package com.example.alkomatonline

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.INVISIBLE
import android.graphics.Color
import android.view.View
import android.view.View.VISIBLE
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {


    var listParties=ArrayList<Party>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var clicked=0
        var id=SizeOfDat("%")
        var isFinish="true"

        if(id!==0){
         isFinish= CheckPart(id,"IsFinish")}





if(isFinish=="true") {
//-----Wyswietlanie tytułu nowej imprezy----------

    buStart.setOnClickListener {
        if (clicked == 0) {
            buStart.setBackgroundColor(Color.RED)
            partyName.visibility = VISIBLE
            buStartParty.visibility = VISIBLE
            clicked = 1
        } else {
            buStart.setBackgroundColor(Color.GREEN)
            partyName.visibility = INVISIBLE
            buStartParty.visibility = INVISIBLE
            clicked = 0
        }
    }


//---------Tworzenie nowej imprezy----------------

    buStartParty.setOnClickListener {
       if(id==0){
        StartNewParty(id,0.1,0.7)}
        else{
           val weight=CheckPart(id,"Weight").toDouble()
           val gender=CheckPart(id,"Gender").toDouble()

           StartNewParty(id,weight,gender)
       }
    }
}else{

    buStart.visibility= INVISIBLE
    partyName.visibility= INVISIBLE
    buStartParty.visibility= INVISIBLE
    buContinue.visibility= VISIBLE

    buContinue.setOnClickListener {
        var intent = Intent(applicationContext, AlkOnline::class.java)
        startActivity(intent)
    }
}

//-------------------Otwieranie nowej aktywności - alkomatu offline -----------------

        alkOFLINE.setOnClickListener {
            var alkomatOF: Intent = Intent(applicationContext, AlkOfline::class.java)
            startActivity(alkomatOF)
        }

//--------------------------------------------------------------------------------




        //-------------------Otwieranie nowej aktywności - historia -----------------

        buHistory.setOnClickListener {
            var history: Intent = Intent(applicationContext, History::class.java)
            startActivity(history)
        }



        //-------------------Otwieranie nowej aktywności- najbliższy monopolowy -----------------

        buMap.setOnClickListener {
            var intent: Intent = Intent(applicationContext, MapsActivity::class.java)
            startActivity(intent)
        }






    }


    override fun onResume() {
        super.onResume()

        partyName.visibility = INVISIBLE
        buStartParty.visibility = INVISIBLE
        buStart.setBackgroundColor(Color.GREEN)

        var clicked=0
        var id=SizeOfDat("%")
        var isFinish="true"

        if(id!==0){
            isFinish= CheckPart(id,"IsFinish")}



        if(isFinish=="true") {
            buContinue.visibility= INVISIBLE
            buStart.visibility= VISIBLE
            buStart.setBackgroundColor(Color.GREEN)

            buStart.setOnClickListener {
                if (clicked == 0) {
                    buStart.setBackgroundColor(Color.RED)

                    partyName.visibility = VISIBLE
                    buStartParty.visibility = VISIBLE
                    clicked = 1
                } else {
                    buStart.setBackgroundColor(Color.GREEN)

                    partyName.visibility = INVISIBLE
                    buStartParty.visibility = INVISIBLE
                    clicked = 0
                }
            }

            buStartParty.setOnClickListener {
                if(id==0){
                    StartNewParty(id,0.1,0.7)}
                else{
                    val weight=CheckPart(id,"Weight").toDouble()
                    val gender=CheckPart(id,"Gender").toDouble()

                    StartNewParty(id,weight,gender)
                }
            }
        }else{

            buStart.visibility= INVISIBLE
            partyName.visibility= INVISIBLE
            buStartParty.visibility= INVISIBLE
            buContinue.visibility= VISIBLE

            buContinue.setOnClickListener {
                var intent = Intent(applicationContext, AlkOnline::class.java)
                startActivity(intent)
            }
        }
    }



    fun SizeOfDat(title:String):Int{
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

    fun CheckPart(id:Int,columnName: String):String{
        val dbManager=DbManager(this)
        val cursor = dbManager.getRaw(id)
        cursor!!.moveToLast()
        val answear:String= cursor.getString(cursor.getColumnIndex(columnName))
        return  answear
    }

    fun StartNewParty(id:Int,weight:Double,gender:Double){
        var dbManager = DbManager(this)
        val startDate = SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Calendar.getInstance().time)
        val pureAlcohol = 0.0
        val promiles = 0.0
        val finishDate = "XX.XX.XXXX XX:XX"
        val sobriety ="??.??.???? ??:??"
        val isFinish = "false"


        var values = ContentValues()
        values.put("Title", partyName.text.toString())
        values.put("Weight", weight)
        values.put("Gender", gender)
        values.put("StartDate", startDate)
        values.put("PureAlcohol", pureAlcohol)
        values.put("Promiles", promiles)
        values.put("FinishDate", finishDate)
        values.put("Sobriety", sobriety)
        values.put("IsFinish", isFinish)

        val ID = dbManager.Insert(values)

        if (ID > 0) {
            Toast.makeText(this, "Rozpoczynamy imprezę!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Can't start party :(", Toast.LENGTH_LONG).show()
        }


        if(id==0) {
            var intent = Intent(applicationContext, Weight_gender::class.java)
            startActivity(intent)

        } else{
        var intent = Intent(applicationContext, AlkOnline::class.java)
        startActivity(intent)}
    }



    }




