package com.weebsarva.wings.android.myapplication3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class FifthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fifth)
        val btnBack4 :Button = findViewById(R.id.btnBack4)

        //3) 戻るボタン (アクティビティの終了)
        btnBack4.setOnClickListener {
            finish()
        }
    }
}