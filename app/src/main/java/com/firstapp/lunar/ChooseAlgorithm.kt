package com.firstapp.lunar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_choose_algorithm.*

class ChooseAlgorithm : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_algorithm)
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
    }
}
