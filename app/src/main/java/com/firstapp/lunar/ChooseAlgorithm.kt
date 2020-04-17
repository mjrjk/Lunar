package com.firstapp.lunar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_choose_algorithm.*
import kotlinx.android.synthetic.main.activity_main.*

class ChooseAlgorithm : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_algorithm)

        if (MyApp.Companion.global == "sim"){
            radioConway.isChecked = false
            radioTrig1.isChecked = false
            radioTrig2.isChecked = false
            radioSimple.isChecked = true
        }
        if (MyApp.Companion.global == "con"){
            radioConway.isChecked = true
            radioTrig1.isChecked = false
            radioTrig2.isChecked = false
            radioSimple.isChecked = false
        }
        if (MyApp.Companion.global == "tr1"){
            radioConway.isChecked = false
            radioTrig1.isChecked = true
            radioTrig2.isChecked = false
            radioSimple.isChecked = false
        }
        if (MyApp.Companion.global == "tr2"){
            radioConway.isChecked = false
            radioTrig1.isChecked = false
            radioTrig2.isChecked = true
            radioSimple.isChecked = false
        }
        val radioSimple: RadioButton = findViewById(R.id.radioSimple)
        val radioConway: RadioButton = findViewById(R.id.radioConway)
        val radioTrig1: RadioButton = findViewById(R.id.radioTrig1)
        val radioTrig2: RadioButton = findViewById(R.id.radioTrig2)
        radioSimple.setOnClickListener{
            radioConway.isChecked = false
            radioTrig1.isChecked = false
            radioTrig2.isChecked = false
            MyApp.Companion.global = "sim"
            val intent = Intent(this, MainActivity :: class.java)
            startActivity(intent)
        }
        radioConway.setOnClickListener{
            radioSimple.isChecked = false
            radioTrig1.isChecked = false
            radioTrig2.isChecked = false
            MyApp.Companion.global = "con"
            val intent = Intent(this, MainActivity :: class.java)
            startActivity(intent)
        }
        radioTrig1.setOnClickListener{
            radioConway.isChecked = false
            radioSimple.isChecked = false
            radioTrig2.isChecked = false
            MyApp.Companion.global = "tr1"
            val intent = Intent(this, MainActivity :: class.java)
            startActivity(intent)
        }
        radioTrig2.setOnClickListener{
            radioConway.isChecked = false
            radioTrig1.isChecked = false
            radioSimple.isChecked = false
            MyApp.Companion.global = "tr2"
            val intent = Intent(this, MainActivity :: class.java)
            startActivity(intent)
        }
    }

    public fun changeRadio(v: View) {
        val myId = v.id
        if(R.id.radioSimple != myId) {
            radioSimple.isChecked = false
        }
        if(R.id.radioConway != myId) {
            radioConway.isChecked = false
        }
        if(R.id.radioTrig1 != myId) {
            radioTrig1.isChecked = false
        }
        if(R.id.radioTrig2 != myId) {
            radioTrig2.isChecked = false
        }
        if(R.id.radioSimple == myId) {
            MyApp.Companion.global == "sim"
        }
        else if(R.id.radioConway == myId) {
            MyApp.Companion.global == "con"
        }
        else if(R.id.radioTrig1 == myId) {
            MyApp.Companion.global == "tr1"
        }
        else if(R.id.radioTrig2 == myId) {
            MyApp.Companion.global == "tr2"
        }
    }
}
