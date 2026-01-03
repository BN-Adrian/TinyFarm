package com.example.tinyfarm.game

class CharacterController {

    enum class Direction { UP, DOWN, LEFT, RIGHT }

    var x = 0f
    var y = 0f
    //5*128f
    private val speed = 10f

    var direction: Direction = Direction.DOWN

    private var frameIndex = 0
    private var lastFrameTime = 0L

    private val frameDelayMs = 120L
    // viteza animației


    fun move(dir: Direction, f: Float) {
        direction = dir
        when (dir) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }
    }

    fun updateAnimation(now: Long) {
        if (now - lastFrameTime >= frameDelayMs) {
            frameIndex = (frameIndex + 1) % 6
            lastFrameTime = now
        }
    }

    fun getFrameIndex(): Int = frameIndex
}
