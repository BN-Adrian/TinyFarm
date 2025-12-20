package com.example.tinyfarm

import android.app.Activity
import android.widget.ImageView
import android.os.Handler
import android.os.Looper

class CharacterActivity(private val activity:Activity){
    private val character: ImageView=activity.findViewById(R.id.character)
    private enum class Direction { UP, DOWN, LEFT, RIGHT }
    private val moveDistance = 10f
    private val handler = Handler(Looper.getMainLooper())
    private var moving = false
    private var direction: Direction? = null


    fun centerCharacter() {
        character.post {
            val parent = character.parent as? android.view.View
            val centerX = (parent?.width ?: 0) / 2f - character.width / 2f
            val centerY = (parent?.height ?: 0) / 2f - character.height / 2f
            character.translationX = centerX - character.left
            character.translationY = centerY - character.top
        }
    }
    private val moveRunnable = object : Runnable {
        override fun run() {
            if (!moving) return
            when (direction) {
                Direction.UP -> character.translationY -= moveDistance
                Direction.DOWN -> character.translationY += moveDistance
                Direction.LEFT -> character.translationX -= moveDistance
                Direction.RIGHT -> character.translationX += moveDistance
                else -> {}
            }
            handler.postDelayed(this, 16) // ~60 cadre pe secundă
        }
    }

    private fun startMoving(dir: Direction) {
        if (moving) return
        direction = dir
        moving = true
        handler.post(moveRunnable)
    }
    fun stopMoving() {
        moving = false
        direction = null
    }
    fun moveUpStart() = startMoving(Direction.UP)
    fun moveDownStart() = startMoving(Direction.DOWN)
    fun moveLeftStart() = startMoving(Direction.LEFT)
    fun moveRightStart() = startMoving(Direction.RIGHT)


}