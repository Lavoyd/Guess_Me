/*
    Ali Darwiche S00051281
    Bashayer Abdulkareem S00050555
    Zeinab Deris S00053357
 */
package com.example.guessme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
    }
    fun options(view: View) {

        var menuIntent = Intent(this, Fruits::class.java)
        when(view.id) {
            R.id.AImageView -> {menuIntent.putExtra("category_index", 0)}
            R.id.FImageView -> {menuIntent.putExtra("category_index", 1)}
            R.id.NImageView  -> {menuIntent.putExtra("category_index", 2)}
        }
        startActivity(menuIntent)

    }
}