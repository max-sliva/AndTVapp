package com.example.andtvapp

import android.graphics.RectF
import java.util.*

class Ball internal constructor(var x: Int, var y: Int, width: Int, height: Int) :
    RectF(x.toFloat(), y.toFloat(), (x+width).toFloat(), (y+height).toFloat()) {
    var random: Random
    var xVelocity = 0
    var yVelocity = 0
    var initialSpeed = 2
    fun setXDirection(randomXDirection: Int) {
        xVelocity = randomXDirection
    }

    fun setYDirection(randomYDirection: Int) {
        yVelocity = randomYDirection
    }

    fun move() {
        x += xVelocity
        y += yVelocity
    }

//    fun draw(g: Graphics) {
//        g.setColor(Color.white)
//        g.fillOval(x, y, height, width)
//    }

    init {
        random = Random()
        var randomXDirection: Int = random.nextInt(2)
        if (randomXDirection == 0) randomXDirection--
        setXDirection(randomXDirection * initialSpeed)
        var randomYDirection: Int = random.nextInt(2)
        if (randomYDirection == 0) randomYDirection--
        setYDirection(randomYDirection * initialSpeed)
    }
}