package com.example.alkomatonline

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.ticket.view.*

class History : AppCompatActivity() {

   var partyList=ArrayList<Party>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

       LoadQuery("%")
    }


    fun LoadQuery(title:String){
        var dbManager=DbManager(this)
        val projections=arrayOf("ID","Title","Weight","Gender","StartDate","PureAlcohol","Promiles","FinishDate","Sobriety","IsFinish")
        val selectionArgs = arrayOf(title)
        val cursor=dbManager.Query(projections,"Title like ?", selectionArgs, "ID")

        partyList.clear()
        if(cursor.moveToFirst()){

            do{
                val id=cursor.getInt(cursor.getColumnIndex("ID"))
                val title=cursor.getString(cursor.getColumnIndex("Title"))
                val weight=cursor.getDouble(cursor.getColumnIndex("Weight"))
                val gender=cursor.getDouble(cursor.getColumnIndex("Gender"))
                val startDate=cursor.getString(cursor.getColumnIndex("StartDate"))
                val pureAlcohol=cursor.getDouble(cursor.getColumnIndex("PureAlcohol"))
                val promiles=cursor.getDouble(cursor.getColumnIndex("Promiles"))
                val finishDate=cursor.getString(cursor.getColumnIndex("FinishDate"))
                val sobriety =cursor.getString(cursor.getColumnIndex("Sobriety"))
                val isFinish:String=cursor.getString(cursor.getColumnIndex("IsFinish"))
                partyList.add(Party(id,weight,gender,title,startDate,pureAlcohol,promiles,finishDate,sobriety,isFinish))
            }while(cursor.moveToNext())
        }
        var myPartyAdapter = MyPartyAdapter(partyList)
        lvParty.adapter=myPartyAdapter

    }



inner class MyPartyAdapter:BaseAdapter{
var partyList=ArrayList<Party>()
    constructor(partyList: ArrayList<Party>):super(){
        this.partyList=partyList

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var myViev=layoutInflater.inflate(R.layout.ticket, null)
        var myNode=partyList[position]
        myViev.hPartyName.text=myNode.partyName
        myViev.hStartDate.text=myNode.startDate
        myViev.hFinishDate.text=myNode.finishDate
        myViev.hPureAlcohol.text=myNode.pureAlcohol.toString()
        myViev.hPromiles.text= myNode.promiles.toString()
        myViev.hSobriety.text=myNode.sobrierty

        if(myNode.isFinish == "false"){
            myViev.buIsFinish.setBackgroundColor(Color.GREEN)
            myViev.buIsFinish.setText("TRWA")
        }



        return myViev
    }

    override fun getItem(position: Int): Any {
       return partyList[position]
    }

    override fun getItemId(position: Int): Long {
      return position.toLong()
    }

    override fun getCount(): Int {
        return partyList.size
    }
}




}