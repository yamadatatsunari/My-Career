package com.weebsarva.wings.android.myapplication3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ThirdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)
        val btnBack2 :Button = findViewById(R.id.btnBack2)

        //3) 戻るボタン (アクティビティの終了)
        btnBack2.setOnClickListener {
            finish()
        }
    }
}