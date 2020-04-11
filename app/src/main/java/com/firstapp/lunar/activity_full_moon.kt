package com.firstapp.lunar

import android.app.Notification
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_full_moon.*

class activity_full_moon : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_moon)

        increase()
        decrease()
    }

    private fun increase(){

        val buttonPlus: Button = findViewById(R.id.buttonPlus)

        buttonPlus.setOnClickListener(){
        val editText: EditText = findViewById(R.id.editText)
            val year = editText.text.toString()
            val increased = year.toInt() + 1
        editText.setText(increased.toString())
        }
    }

    private fun decrease(){
        val buttonMinus: Button = findViewById(R.id.buttonMinus)

        buttonMinus.setOnClickListener(){
            val editText: EditText = findViewById(R.id.editText)
            val year = editText.text.toString()
            val decreased = year.toInt() - 1
            editText.setText(decreased.toString())
        }
    }

}
