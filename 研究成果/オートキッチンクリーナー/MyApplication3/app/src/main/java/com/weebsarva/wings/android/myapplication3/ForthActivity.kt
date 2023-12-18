package com.weebsarva.wings.android.myapplication3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ForthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forth)
        val btnBack3 :Button = findViewById(R.id.btnBack3)

        //3) 戻るボタン (アクティビティの終了)
        btnBack3.setOnClickListener {
            finish()
        }
    }
}