package com.firstapp.lunar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnOpenActivity = findViewById<TextView>(R.id.button)
        btnOpenActivity.setOnClickListener {
            val intent = Intent(this, activity_full_moon :: class.java)
            startActivity(intent)
        }
    }
}
