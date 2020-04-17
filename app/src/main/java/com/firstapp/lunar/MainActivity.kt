package com.firstapp.lunar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //val simpleButton: RadioButton = findViewById(R.id.radioSimple)
        //if(intent.getStringExtra("refresh") == "yes"){
          //  this.recreate()
        //}

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnOpenActivity = findViewById<TextView>(R.id.button)
        btnOpenActivity.setOnClickListener {
            val intent = Intent(this, activity_full_moon :: class.java)
            startActivity(intent)
            this.finish()

        }

        var todayPhase: Int
        val currentDate = Date()
        var nextFull: String = currentDate.toString()
        var nextNew: String = currentDate.toString()

        if (MyApp.Companion.global == "sim"){
            todayPhase = trig2(currentDate)
            nextFull = nextFullMoon(currentDate, "sim" )
            nextNew = nextNewMoon(currentDate, "sim")
        }
        else if (MyApp.Companion.global == "con"){
            todayPhase = conway(currentDate)
            nextFull = nextFullMoon(currentDate, "con" )
            nextNew = nextNewMoon(currentDate, "con")
        }
        else if (MyApp.Companion.global == "tr1"){
            todayPhase = trig1(currentDate)
            nextFull = nextFullMoon(currentDate, "tr1" )
            nextNew = nextNewMoon(currentDate, "tr1")
        }
        else if (MyApp.Companion.global == "tr2"){
            todayPhase = trig2(currentDate)
            nextFull = nextFullMoon(currentDate, "tr2" )
            nextNew = nextNewMoon(currentDate, "tr2")
        }
        else todayPhase = 0
        val image = showImage(todayPhase)
        var percents = toPercents(todayPhase)
        textView.setText("Dzisiaj: " + todayPhase.toString() + "%")
        textView2.setText("Następna pełnia: " + nextFull)
        textView3.setText("Następny nów: " + nextNew)
        imageView.setImageDrawable(R.drawable.n0.toDrawable())

    }

    }

    private fun simple(date: Date): Int
    {
        val lp = 2551443;
        val newMoon = SimpleDateFormat("hh:mm:ss dd-MM-yyyy").parse("20:35:00 07-01-1970")
        val phase = ((date.time - newMoon.time)/1000) % lp;
        return ((phase/(24*3600)) + 1).toInt()
    }

    private fun toPercents(dayofperiod: Int): Int {
        var result: Int
        if (dayofperiod < 15){
           result = (dayofperiod/15*100).toInt()
        }
        else{
            result = (29 - dayofperiod)/15*100
        }
        return result
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
private fun nextFullMoon(date: Date, alg: String): String{
    val calendar: Calendar = Calendar.getInstance()
    calendar.time = date
    var next = false
    var dateOfFullMoon: Date = calendar.time
    if (alg == "sim"){
        while(!next){
            if (simple(calendar.time) == 15){
                next = true
                dateOfFullMoon = calendar.time
            }
            else
                calendar.add(Calendar.DATE, 1)
        }
    }
    else if (alg == "con"){
        while(!next){
            if (conway(calendar.time) == 15){
                next = true
                dateOfFullMoon = calendar.time
            }
            else
                calendar.add(Calendar.DATE, 1)
        }
    }
    else if (alg == "tr1"){
        while(!next){
            if (trig1(calendar.time) == 15){
                next = true
                dateOfFullMoon = calendar.time
            }
            else
                calendar.add(Calendar.DATE, 1)
        }
    }
    else if (alg == "tr2"){
        while(!next){
            if (trig2(calendar.time) == 15){
                next = true
                dateOfFullMoon = calendar.time

            }
            else
                calendar.add(Calendar.DATE, 1)
        }
    }
    val formatter: DateFormat = SimpleDateFormat("EEE, dd MMM yyyy")
    val today = formatter.format(dateOfFullMoon)

    return today
}
private fun nextNewMoon (date: Date, alg: String): String{
    val calendar: Calendar = Calendar.getInstance()
    calendar.time = date
    var next = false
    var dateOfNewMoon: Date = calendar.time
    if (alg == "sim"){
        while(!next){
            if ((simple(calendar.time) == 0)||(simple(calendar.time) == 29)){
                next = true
                dateOfNewMoon = calendar.time
            }
            else
                calendar.add(Calendar.DATE, 1)
        }
    }
    else if (alg == "con"){
        while(!next){
            if ((conway(calendar.time) == 0)||(conway(calendar.time) == 29)){
                next = true
                dateOfNewMoon = calendar.time
            }
            else
                calendar.add(Calendar.DATE, 1)
        }
    }
    else if (alg == "tr1"){
        while(!next){
            if ((trig1(calendar.time) == 0)||(trig1(calendar.time) == 29)){
                next = true
                dateOfNewMoon = calendar.time
            }
            else
                calendar.add(Calendar.DATE, 1)
        }
    }
    else if (alg == "tr2"){
        while(!next){
            if ((trig2(calendar.time) == 0)||(trig2(calendar.time) == 29)){
                next = true
                dateOfNewMoon = calendar.time
            }
            else
                calendar.add(Calendar.DATE, 1)
        }
    }

    val formatter: DateFormat = SimpleDateFormat("EEE, dd MMM yyyy")
    val today = formatter.format(dateOfNewMoon);

    return today
}

private fun showImage(phase: Int): String{
    var image = "n0"
    if ((phase > 0)&&(phase <= 3)) image = "n4"
    else if ((phase > 3)&&(phase <= 6)) image = "n17"
    else if ((phase > 6)&&(phase <= 9)) image = "n48"
    else if ((phase > 9)&&(phase <= 12)) image = "n71"
    else if ((phase > 12)&&(phase < 15)) image = "n89"
    else if (phase == 15) image = "n100"
    else if ((phase > 15)&&(phase <= 18)) image = "n90d"
    else if ((phase > 18)&&(phase <= 21)) image = "n75d"
    else if ((phase > 21)&&(phase <= 24)) image = "n48d"
    else if ((phase > 24)&&(phase <= 27)) image = "n21d"
    else if ((phase > 27)&&(phase < 29)) image = "n3d"
    else image = "n0"
    return image
}

