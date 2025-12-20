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

    //map cu fiecare directie de mesr pentru caracter
    private val walkFrames: MutableMap<Direction, List<Int>> = mutableMapOf()
    private var frameIndex =0
    private var frameHandler: Handler?=null

    //incarcre poze miscare
    init{
        walkFrames[Direction.RIGHT]=loadFrames("om_dreapta",6)
        walkFrames[Direction.LEFT]=loadFrames("om_stanga",6)
        walkFrames[Direction.UP]=loadFrames("om_sus",6)
        walkFrames[Direction.DOWN]=loadFrames("om_jos",6)
    }

    private fun loadFrames(prefix: String, count: Int): List<Int> {
        val frames = mutableListOf<Int>()
        for (i in 1..count) {
            val resId = activity.resources.getIdentifier("$prefix$i", "drawable", activity.packageName)
            if (resId != 0) frames.add(resId)
        }
        return frames
    }

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
    //runnable pentru animatia cadrului
    private val frameRunnable = object : Runnable {
        override fun run() {
            direction?.let {
                val frames = walkFrames[it]
                if (!frames.isNullOrEmpty()) {
                    character.setImageResource(frames[frameIndex])
                    frameIndex = (frameIndex + 1) % frames.size
                    frameHandler?.postDelayed(this, 100) // viteza animației
                }
            }
        }
    }

    private fun startMoving(dir: Direction) {
        if (moving) return
        direction = dir
        moving = true
        handler.post(moveRunnable)

        frameHandler = Handler(Looper.getMainLooper())
        frameIndex = 0
        frameHandler?.post(frameRunnable)
    }
    fun stopMoving() {
        moving = false
        direction = null
        frameHandler?.removeCallbacks(frameRunnable)
        frameHandler = null
    }
    fun moveUpStart() = startMoving(Direction.UP)
    fun moveDownStart() = startMoving(Direction.DOWN)
    fun moveLeftStart() = startMoving(Direction.LEFT)
    fun moveRightStart() = startMoving(Direction.RIGHT)


}