package com.weebsarva.wings.android.myapplication3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        //1) Viewの所得
        val btnStart: Button = findViewById(R.id.btnStart)

        //2) ボタンを押したら次の画面へ
        btnStart.setOnClickListener {
            val intent = Intent(this,SecondActivity::class.java)
            startActivity(intent)
        }
//--------------------------------------------------------------------------------------
        //1) Viewの所得
        val btnStart2: Button = findViewById(R.id.btnStart2)

        //2) ボタンを押したら次の画面へ
        btnStart2.setOnClickListener {
            val intent = Intent(this,ThirdActivity::class.java)
            startActivity(intent)
        }
//--------------------------------------------------------------------------------------
        //1) Viewの所得
        val btnStart3: Button = findViewById(R.id.btnStart3)

        //2) ボタンを押したら次の画面へ
        btnStart3.setOnClickListener {
            val intent = Intent(this,ForthActivity::class.java)
            startActivity(intent)
        }
//--------------------------------------------------------------------------------------
        //1) Viewの所得
        val btnStart4: Button = findViewById(R.id.btnStart4)

        //2) ボタンを押したら次の画面へ
        btnStart4.setOnClickListener {
            val intent = Intent(this,FifthActivity::class.java)
            startActivity(intent)
        }
//--------------------------------------------------------------------------------------
        //1) Viewの所得
        val btnStart6: Button = findViewById(R.id.btnStart6)

        //2) ボタンを押したら次の画面へ
        btnStart6.setOnClickListener {
            val intent = Intent(this,SixthActivity::class.java)
            startActivity(intent)
        }

        //3) 戻るボタン (アクティビティの終了)
        val btnStart5 :Button = findViewById(R.id.btnStart5)

        btnStart5.setOnClickListener {
            finish()
        }

    }
}