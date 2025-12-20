package com.example.tinyfarm

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
        setContentView(R.layout.activity_main)

        val startButton=findViewById<Button>(R.id.StartButton)


        val settingButton=findViewById<Button>(R.id.settingButton)
        settingButton.setOnClickListener {
            openSettingDialog()
        }



        startButton.setOnClickListener {
            val intent= Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

    }
    @SuppressLint("ClickableViewAccessibility")
    private fun openSettingDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_setting, null)
        val dialog = Dialog(this)
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

}