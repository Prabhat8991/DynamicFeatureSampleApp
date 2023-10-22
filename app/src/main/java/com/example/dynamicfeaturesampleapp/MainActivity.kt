package com.example.dynamicfeaturesampleapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button_main)

        button.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    DynamicFeatureHolderActivity::class.java
                )
            )
        }

    }
}