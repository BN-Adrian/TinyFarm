package com.example.tinyfarm

import com.example.tinyfarm.game.CharacterController
import com.example.tinyfarm.ui.GameView



import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity



class GameActivity : AppCompatActivity(){


    //caracter versiune noua
    private lateinit var gameView: GameView
    private lateinit var characterController: CharacterController

    private val moveHandler = Handler(Looper.getMainLooper())
    private var moving = false
    private var moveDir: CharacterController.Direction? = null




    private val moveRunnable = object : Runnable {
        override fun run() {
            if (!moving || moveDir == null) return
            gameView.controller.move(moveDir!!, 10f)
            gameView.postInvalidateOnAnimation()
            moveHandler.postDelayed(this, 16)
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
        setContentView(R.layout.activity_game)
        gameView=findViewById(R.id.gameView)



        //buton setari
        val settingButton=findViewById<Button>(R.id.settingButton)
        settingButton.setOnClickListener {
            openSettingDialog()
        }


        val buttonUp = findViewById<Button>(R.id.buttonUp)
        val buttonDown = findViewById<Button>(R.id.buttonDown)
        val buttonLeft = findViewById<Button>(R.id.buttonLeft)
        val buttonRight = findViewById<Button>(R.id.buttonRight)

        //versiune noua
        buttonUp.setOnTouchListener(moveListener { isPressed ->
            if (isPressed) startMoving(CharacterController.Direction.UP) else stopMoving()
        })
        buttonDown.setOnTouchListener(moveListener { isPressed ->
            if (isPressed) startMoving(CharacterController.Direction.DOWN) else stopMoving()
        })
        buttonLeft.setOnTouchListener(moveListener { isPressed ->
            if (isPressed) startMoving(CharacterController.Direction.LEFT) else stopMoving()
        })
        buttonRight.setOnTouchListener(moveListener { isPressed ->
            if (isPressed) startMoving(CharacterController.Direction.RIGHT) else stopMoving()
        })

    }
    private fun startMoving(dir: CharacterController.Direction) {
        moveDir = dir
        if (moving) return
        moving = true
        moveHandler.post(moveRunnable)
    }

    private fun stopMoving() {
        moving = false
        moveDir = null
        moveHandler.removeCallbacks(moveRunnable)
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun openSettingDialog(){
        val dialogView=layoutInflater.inflate(R.layout.dialog_setting,null)
        val dialog=Dialog(this)
        dialog.setContentView(dialogView)

        dialog.window?.decorView?.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )

        dialog.show()


        val closeButton = dialogView.findViewById<Button>(R.id.closeButton)
        closeButton.setOnClickListener {
            dialog.dismiss()
        }
        val quiteButton=dialogView.findViewById<Button>(R.id.quitButton)
        quiteButton.setOnTouchListener { _, event->
            if(event.action== MotionEvent.ACTION_DOWN){
                finishAffinity()
            }
            true
        }

    }
    private fun moveListener(action: (Boolean) -> Unit): View.OnTouchListener{
        return View.OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> action(true)
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    action(false)
                    v.performClick()
                }
            }
            true
        }
    }



}