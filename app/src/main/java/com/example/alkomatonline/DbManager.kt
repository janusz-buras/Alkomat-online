package com.example.alkomatonline

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast


class DbManager {
    val dbTable = "MyParties"
    val colID = "ID"
    val dbTitle = "Title"
    val dbWeight="Weight"
    val dbGender="Gender"
    val dbStartDate = "StartDate"
    val dbPureAlcohol = "PureAlcohol"
    val dbPromiles = "Promiles"
    val dbFinishDate = "FinishDate"
    val dbIsFinish= "IsFinish"
    val dbSobriety = "Sobriety"


    val SQL_CREATE_TABLE: String = "CREATE TABLE IF NOT EXISTS " + dbTable + " (" + colID + " INTEGER PRIMARY KEY,"+ dbTitle + " TEXT, " + dbWeight +  " TEXT, "  + dbGender +" TEXT, " +dbStartDate  + " TEXT, " + dbPureAlcohol + " TEXT, " + dbPromiles  + " TEXT, " + dbFinishDate + " TEXT, "+ dbSobriety + " TEXT, " + dbIsFinish + " TEXT);"



    var sqlDB: SQLiteDatabase? = null

    constructor(context: Context) {
        val db = DataBaseHelper(context)
     sqlDB = db.readableDatabase
    }


    inner class DataBaseHelper : SQLiteOpenHelper {

        private var context: Context? = null

        constructor(context: Context) : super(context, dbTable, null, 1) {
            this.context = context
        }

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(SQL_CREATE_TABLE)
            Toast.makeText(context, "database is created", Toast.LENGTH_LONG).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("Drop table IF EXISTS" + dbTable)
        }

    }


    fun Insert(values: ContentValues): Long {
        val ID = sqlDB!!.insert(dbTable, "", values)

        return ID
    }

    fun Query(projection: Array<String>, selection: String, selectionArgs: Array<String>, sorOrder: String): Cursor {

        val qb = SQLiteQueryBuilder()
        qb.tables = dbTable
        val cursor = qb.query(sqlDB, projection, selection, selectionArgs, null, null, sorOrder)

        return cursor
    }

fun getRaw(id:Int): Cursor? {
    val query="Select * from $dbTable WHERE $colID=?"
    val cursor= sqlDB!!.rawQuery(query, arrayOf(id.toString()))
    return cursor
}


    fun Update(values: ContentValues, selection: String, selectionArgs: Array<String>): Int {

        val count = sqlDB!!.update(dbTable, values, selection, selectionArgs)
        return count
    }

}
