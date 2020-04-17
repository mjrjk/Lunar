package com.firstapp.lunar

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class activity_full_moon : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_moon)
        var mApp = MyApp()
        var strGlobalVar = MyApp.Companion.global
        showFullMoons(2020)

        increase()
        decrease()

        val btnOpenActivity = findViewById<TextView>(R.id.buttonAlg)
        btnOpenActivity.setOnClickListener {
            val intent = Intent(this, ChooseAlgorithm :: class.java)
            startActivity(intent)
        }

    }
    private fun simple(date: Date): Int
    {
        val lp = 2551443;
        val newMoon = SimpleDateFormat("hh:mm:ss dd-MM-yyyy").parse("20:35:00 07-01-1970")
        val phase = ((date.time - newMoon.time)/1000) % lp;
        return ((phase/(24*3600)) + 1).toInt()
    }

    private fun conway(date: Date): Int {
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date
        val year = calendar.get(Calendar.YEAR)
        var r = year % 100
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        if (r>9){ r -= 19;}
        r = ((r * 11) % 30) + month + day;
        if (month<3){r += 2;}
        if(year<2000) r -= 4
        else r = (r - 8.3).toInt()
        if (r<30) r + 30
        return if (r <0) r + 30
        else r
    }

    private fun julday(year: Int, month : Int, day: Int): Double {
        var copyyear = year
        if (copyyear < 0) {
            copyyear += 1
        }
        var jy = copyyear
        var jm = month +1;
        if (month <= 2) {jy--;	jm += 12;	}
        var jul = Math.floor(365.25 *jy) + Math.floor(30.6001 * jm) + day + 1720995;
        if (day+31*(month+12*year) >= (15+31*(10+12*1582))) {
            var ja = Math.floor(0.01 * jy);
            jul = jul + 2 - ja + Math.floor(0.25 * ja);
        }
        return jul;
    }
    private fun trig1 (date: Date): Int{
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val thisJD: Double  = julday(year, month, day)
        val degToRad = 3.14159265 / 180
        val K0: Double
        val T: Double
        val T2: Double
        val T3: Double
        val J0: Double
        val F0: Double
        val M0: Double
        val M1: Double
        val B1: Double
        var oldJ: Int = 0
        K0 = Math.floor((year - 1900) * 12.3685)
        T = (year - 1899.5) / 100
        T2 = T * T
        T3 = T * T * T
        J0 = 2415020 + 29 * K0
        F0 = 0.0001178 * T2 - 0.000000155 * T3 + (0.75933 + 0.53058868 * K0) - (0.000837 * T + 0.000335 * T2)
        M0 = 360 * (K0 * 0.08084821133) + 359.2242 - 0.0000333 * T2 - 0.00000347 * T3
        M1 = 360 * (K0 * 0.07171366128) + 306.0253 + 0.0107306 * T2 + 0.00001236 * T3
        B1 = 360 * (K0 * 0.08519585128) + 21.2964 - 0.0016528 * T2 - 0.00000239 * T3
        var phase = 0
        var jday = 0
        while (jday < thisJD) {
            var F= F0 + 1.530588 * phase
            val M5= (M0 + phase * 29.10535608) * degToRad
            val M6 = (M1 + phase * 385.81691806) * degToRad
            val B6 = (B1 + phase * 390.67050646) * degToRad
            F -= 0.4068 * Math.sin(M6) + (0.1734 - 0.000393 * T) * Math.sin(M5)
            F += 0.0161 * Math.sin(2 * M6.toDouble()) + 0.0104 * Math.sin(2 * B6.toDouble())
            F -= 0.0074 * Math.sin(M5 - M6.toDouble()) - 0.0051 * Math.sin(M5 + M6.toDouble())
            F += 0.0021 * Math.sin(2 * M5.toDouble()) + 0.0010 * Math.sin(2 * B6 - M6.toDouble())
            F += 0.5 / 1440
            oldJ = jday
            jday = (J0 + 28 * phase + Math.floor(F)).toInt()
            phase++
        }
        return ((thisJD - oldJ) % 30).toInt()
    }
    private fun trig2 (date: Date): Int {
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val n = Math.floor(12.37 * (year -1900 + ((1.0 * month - 0.5)/12.0)))
        val RAD = 3.14159265/180.0
        val t = n / 1236.85
        val t2 = t * t
        val as1 = 359.2242 + 29.105356 * n
        val am = 306.0253 + 385.816918 * n + 0.010730 * t2;
        var xtra = 0.75933 + 1.53058868 * n + ((1.178e-4) - (1.55e-7) * t) * t2;
        xtra += (0.1734 - 3.93e-4 * t) * Math.sin(RAD * as1) - 0.4068 * Math.sin(RAD * am);
        val i: Double
        if (xtra > 0.0) i = Math.floor(xtra)
        else i = Math.ceil(xtra - 1.0)
        val j1 = julday(year,month,day);
        val jd = (2415020 + 28 * n) + i;
        return ((j1-jd + 30)%30).toInt()
    }

    private fun showFullMoons(year: Int){
        var fullMoons = arrayListOf<String>()
        val listview: ListView = findViewById(R.id.listView)
        var cal = Calendar.getInstance()
        cal.set(year, 1, 1)
        val formatter: DateFormat = SimpleDateFormat("EEE, dd MMM yyyy")
        val today :String
        if (MyApp.Companion.global == "sim"){
            while (cal.get(Calendar.YEAR) == year) {
                if (simple(cal.time) == 15) {
                    var new = formatter.format(cal.time)
                    fullMoons.add(new)
                }
                cal.add(Calendar.DATE, 1)
            }
        }
        else if (MyApp.Companion.global == "con") {
            while (cal.get(Calendar.YEAR) == year) {
                if (conway(cal.time) == 15) {
                    var new = formatter.format(cal.time)
                    fullMoons.add(new)
                }
                cal.add(Calendar.DATE, 1)
            }
        }
        else if (MyApp.Companion.global == "tr1"){
            while (cal.get(Calendar.YEAR) == year) {
                if (trig1(cal.time) == 15) {
                    var new = formatter.format(cal.time)
                    fullMoons.add(new)
                }
                cal.add(Calendar.DATE, 1)
            }
        }
        else if (MyApp.Companion.global == "tr2"){
            while (cal.get(Calendar.YEAR) == year) {
                if (trig2(cal.time) == 15) {
                    var new = formatter.format(cal.time)
                    fullMoons.add(new)
                }
                cal.add(Calendar.DATE, 1)
            }
        }

        listview.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fullMoons)
    }
    private fun increase(){

        val buttonPlus: Button = findViewById(R.id.buttonPlus)


        buttonPlus.setOnClickListener() {
            val editText: EditText = findViewById(R.id.editText)
            val year = editText.text.toString()
            val increased = year.toInt() + 1
            editText.setText(increased.toString())

            showFullMoons(increased)
        }
    }

    private fun decrease(){
        val buttonMinus: Button = findViewById(R.id.buttonMinus)

        buttonMinus.setOnClickListener(){
            val editText: EditText = findViewById(R.id.editText)
            val year = editText.text.toString()
            val decreased = year.toInt() - 1
            editText.setText(decreased.toString())

            showFullMoons(decreased)
        }
    }


}
