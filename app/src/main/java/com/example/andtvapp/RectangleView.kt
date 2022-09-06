package com.example.andtvapp

import android.content.Context
import android.graphics.*
import android.view.SurfaceHolder

import android.view.SurfaceView
import java.util.*


class RectangleView(context: Context?) : SurfaceView(context), SurfaceHolder.Callback {
    val x1 = 0F
    var yLeft = 0f
    var yRight = 0f
    val leftRectangle = Rect()
    val rightRectangle = Rect()
    var ball : Ball? = null
    private var paint: Paint? = null
    val ballRadius = 100
    var ballX = 0
    var ballY = 0
    var fieldWidth = 0
    var fieldHeight = 0
    val BALL_DIAMETER = 100
    val PADDLE_HEIGHT = 300

    fun draw() {
        val canvas: Canvas = holder.lockCanvas()
        canvas.drawColor(Color.BLACK)
        canvas.save()
//        canvas.translate(x1, y1)
//        canvas.drawRect(0F, 0f, 100f, 100f, paint!!)
        val paintShape = Paint()
        paintShape.color = Color.RED
        paintShape.style = Paint.Style.FILL

        leftRectangle.set(0, yLeft.toInt(), 100, (yLeft+300).toInt())
        canvas.drawRect(leftRectangle, paintShape)

        paintShape.color = Color.BLUE
        paintShape.style = Paint.Style.FILL
        rightRectangle.set(canvas.width-100, yRight.toInt(), canvas.width, (yRight+300).toInt())
        canvas.drawRect(rightRectangle, paintShape)
        paintShape.color = Color.WHITE
        paintShape.style = Paint.Style.FILL
//        var ball = RectF((canvas.width/2-50).toFloat(), (canvas.height / 2-50).toFloat(),(canvas.width/2+50).toFloat(), (canvas.height / 2+50).toFloat())
//        var ball = RectF(200f, 200f,400f, 400f)
        ball!!.set(ballX.toFloat(), ballY.toFloat(),(ballX+ballRadius/2).toFloat(), (ballY+ballRadius/2).toFloat())
        canvas.drawOval(ball!!, paintShape)
        canvas.restore()
        holder.unlockCanvasAndPost(canvas)
        //y1++
    }

    fun leftDown10(){
//        if (leftRectangle.top >= fieldHeight - PADDLE_HEIGHT) leftRectangle.top = fieldHeight - PADDLE_HEIGHT
//        else {
//            yLeft += 10
//            draw()
//        }
        if (leftRectangle.top <= fieldHeight - PADDLE_HEIGHT) {
            yLeft += 10
          //  draw()
        }
    }

    fun leftUp10(){
        if (leftRectangle.top <= 0) leftRectangle.top = 0
        else {
            yLeft -= 10
          //  draw()
        }
    }

    private var timer: Timer? = null
    private var task: TimerTask? = null
    fun startTimer() { //переделать под корутину
        timer = Timer()
        task = object : TimerTask() {
            override fun run() {
                ball?.move()
                ballX = ball!!.x
                ballY = ball!!.y
                checkCollision()
                draw()
            }
        }
        timer!!.schedule(task, 100, 10)
    }

    fun stopTimer() {
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
    }

    override fun surfaceChanged(
        holder: SurfaceHolder, format: Int, width: Int,
        height: Int
    ) {
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        val canvas = holder.lockCanvas()
        ball = Ball(canvas.width/2-50, canvas.height / 2-50,ballRadius, ballRadius)
        ballX = canvas.width/2-50
        ballY = canvas.height / 2-50
        fieldWidth = canvas.width
        fieldHeight = canvas.height
        holder.unlockCanvasAndPost(canvas)
        draw()
        startTimer()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        stopTimer()
    }

    fun checkCollision() {
        //bounce ball off top & bottom window edges
        if (ball!!.y <= 0) {
            ball!!.setYDirection(-ball!!.yVelocity)
        }
        if (ball!!.y >= fieldHeight - BALL_DIAMETER) {
            ball!!.setYDirection(-ball!!.yVelocity)
        }
        //bounce ball off paddles
        if (ball!!.intersects(leftRectangle.left.toFloat(), leftRectangle.top.toFloat(),
                              leftRectangle.right.toFloat(), leftRectangle.bottom.toFloat()
            )) {
            ball!!.xVelocity = Math.abs(ball!!.xVelocity)
            ball!!.xVelocity++ //optional for more difficulty
            if (ball!!.yVelocity > 0) ball!!.yVelocity++ //optional for more difficulty
            else ball!!.yVelocity--
            ball!!.setXDirection(ball!!.xVelocity)
            ball!!.setYDirection(ball!!.yVelocity)
        }
        if (ball!!.intersects(rightRectangle.left.toFloat(), rightRectangle.top.toFloat(),
                              rightRectangle.right.toFloat(), rightRectangle.bottom.toFloat())) {
            ball!!.xVelocity = Math.abs(ball!!.xVelocity)
            ball!!.xVelocity++ //optional for more difficulty
            if (ball!!.yVelocity > 0) ball!!.yVelocity++ //optional for more difficulty
            else ball!!.yVelocity--
            ball!!.setXDirection(-ball!!.xVelocity)
            ball!!.setYDirection(ball!!.yVelocity)
        }
        //stops paddles at window edges
//        if (leftRectangle.top <= 0) leftRectangle.top = 0
//        if (leftRectangle.top >= fieldHeight - PADDLE_HEIGHT) leftRectangle.top = fieldHeight - PADDLE_HEIGHT
        if (rightRectangle.top <= 0) rightRectangle.top = 0
        if (rightRectangle.top >= fieldHeight - PADDLE_HEIGHT) rightRectangle.top = fieldHeight - PADDLE_HEIGHT
        //give a player 1 point and creates new paddles & ball
//        println("ball x = ${ball!!.x}")
        if (ball!!.x <= 0) {
            println("ball x < 0")
            ball = Ball(fieldWidth/2-50, fieldHeight/ 2-50,ballRadius, ballRadius)
//            score.player2++
//            newPaddles()
//            newBall()
//            System.out.println("Player 2: " + score.player2)
        }
        if (ball!!.x >= fieldWidth - ballRadius) {
            println("ball x out")
            ball = Ball(fieldWidth/2-50, fieldHeight/ 2-50,ballRadius, ballRadius)
//            newPaddles()
//            newBall()
//            System.out.println("Player 1: " + score.player1)
        }
    }

    init {
        paint = Paint()
        paint!!.setColor(Color.RED)
        holder.addCallback(this)
    }
}