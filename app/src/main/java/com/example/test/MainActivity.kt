package com.example.test

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onBtnClick(view: View) {

        if(ed.text.toString() != "") {
            val intent = Intent(this, FeedActivity::class.java)
            intent.putExtra("url", ed.text.toString());
            startActivity(intent);
        }
         else
        {
            Toast.makeText(this,"Введите ссылку",Toast.LENGTH_SHORT).show()
        }
    }
}