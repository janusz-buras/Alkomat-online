package com.example.alkomatonline

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import kotlinx.android.synthetic.main.activity_result_of_drinking.*
import org.json.JSONArray


class ResultOfDrinking : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_of_drinking)

        val extras = intent.extras
        var promiles:Double = extras?.getDouble("PROMILES")!!
        val pureAlcohol = extras?.getDouble("PUREALCOHOL")
        var sobriety: String? = extras?.getString("SOBRIETY")


        val sobrietyTime = sobriety?.substring(0,5)


        textPromiles.text= promiles.toString()
        textPure.text= pureAlcohol.toString()
        textTime.text = sobrietyTime

        setBarChart(promiles)
    }

    private fun setBarChart(p:Double) {

       var hour=0
        var promiles=p

        val entries = ArrayList<BarEntry>()

        do{
            entries.add(BarEntry(hour.toFloat(), promiles.toFloat()))

           hour+=1
            promiles -= 0.14

        }while (promiles>0.001f)




        val barDataSet = BarDataSet(entries, "Promile")



        var data = BarData(barDataSet)
        barChart.data = data

        barChart.animateY(2000)
    }


}






