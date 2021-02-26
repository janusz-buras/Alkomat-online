package com.example.alkomatonline

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_alk_ofline.*
import kotlinx.android.synthetic.main.activity_weight_gender.*

class Weight_gender : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight_gender)
        var id= SizeOfData("%")


        var gender: Double ?= null
        var clicked:Boolean=false

        gWoman.setOnClickListener {
            gWoman.setBackgroundColor(Color.GREEN)
            gMan.setBackgroundColor(Color.GRAY)
            gender = 0.6
            clicked=true
        }

        gMan.setOnClickListener {
            gMan.setBackgroundColor(Color.GREEN)
            gWoman.setBackgroundColor(Color.GRAY)
            gender = 0.7
            clicked=true
        }

        buSave.setOnClickListener {
            val weight = Integer.parseInt(weightData.text.toString())


            var dbManager = DbManager(this)
            var values = ContentValues()
            values.put("Weight", weight)
            values.put("Gender", gender)
            var selectionArs= arrayOf(id.toString())
            val ID = dbManager.Update(values,"ID=?",selectionArs)

            var intent = Intent(applicationContext, AlkOnline::class.java)
            startActivity(intent)
            finish()
        }
    }




    fun SizeOfData(title:String):Int{
        var dbManager=DbManager(this)
        val projections=arrayOf("ID")
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
}




