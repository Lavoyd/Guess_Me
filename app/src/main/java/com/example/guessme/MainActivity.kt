/*
    Ali Darwiche S00051281
    Bashayer Abdulkareem S00050555
    Zeinab Deris S00053357
 */
package com.example.guessme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
    fun menu(view: View) {
        val intent = Intent(this, Menu::class.java)
        startActivity(intent)
    }
    fun onClick(view: View) {
        val intent = Intent(this, Instructions::class.java)
        startActivity(intent)
    }
    fun quit(view: View) {
        finish()
    }
}