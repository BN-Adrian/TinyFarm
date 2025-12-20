package com.example.tinyfarm

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.app.Dialog
import android.view.View
import android.view.MotionEvent


class GameActivity : AppCompatActivity(){
    private lateinit var character: CharacterActivity

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
        setContentView(R.layout.activity_game)

        val settingButton=findViewById<Button>(R.id.settingButton)
        settingButton.setOnClickListener {
            openSettingDialog()
        }

        //caracter
        character= CharacterActivity(this)
        character.centerCharacter()

        val buttonUp = findViewById<Button>(R.id.buttonUp)
        val buttonDown = findViewById<Button>(R.id.buttonDown)
        val buttonLeft = findViewById<Button>(R.id.buttonLeft)
        val buttonRight = findViewById<Button>(R.id.buttonRight)
        buttonUp.setOnTouchListener(moveListener { isPressed ->
            if (isPressed) character.moveUpStart() else character.stopMoving()
        })
        buttonDown.setOnTouchListener(moveListener { isPressed ->
            if (isPressed) character.moveDownStart() else character.stopMoving()
        })
        buttonLeft.setOnTouchListener(moveListener { isPressed ->
            if (isPressed) character.moveLeftStart() else character.stopMoving()
        })
        buttonRight.setOnTouchListener(moveListener { isPressed ->
            if (isPressed) character.moveRightStart() else character.stopMoving()
        })
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