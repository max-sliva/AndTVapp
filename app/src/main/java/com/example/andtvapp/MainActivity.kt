package com.example.andtvapp

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var myview: RectangleView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myview = RectangleView(this)
        setContentView(myview)
//        myview!!.draw()
//        setContentView(R.layout.activity_main)
        println("Hello")
    }

    override fun onPause() {
        super.onPause()
        myview!!.stopTimer()
    }
//    fun onBtnClick(view: View) {
//        val message = (view as Button).text
//        println("text = ${message}")
//        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
//    }

    override fun onKeyDown(keyCode: Int, events: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_DPAD_CENTER -> {
                println("Center")
            }
            KeyEvent.KEYCODE_DPAD_LEFT -> {
                println("LEFT")
            }
            KeyEvent.KEYCODE_DPAD_RIGHT -> {
                println("RIGHT")
            }
            KeyEvent.KEYCODE_DPAD_DOWN -> {
                println("DOWN")
                myview?.leftDown10()
            }
            KeyEvent.KEYCODE_DPAD_UP -> {
                println("UP")
                myview?.leftUp10()
            }
            KeyEvent.FLAG_KEEP_TOUCH_MODE -> {}
        }
        return super.onKeyDown(keyCode, events)
    }
}