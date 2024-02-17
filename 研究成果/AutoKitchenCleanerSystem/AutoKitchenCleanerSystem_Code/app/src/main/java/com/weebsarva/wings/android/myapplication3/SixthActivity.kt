package com.weebsarva.wings.android.myapplication3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SixthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sixth)
        val btnBack5 :Button = findViewById(R.id.btnBack5)

        //3) 戻るボタン (アクティビティの終了)
        btnBack5.setOnClickListener {
            finish()
        }
    }
}