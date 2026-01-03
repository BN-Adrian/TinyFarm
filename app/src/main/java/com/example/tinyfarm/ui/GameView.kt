package com.example.tinyfarm.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.tinyfarm.R
import com.example.tinyfarm.game.CharacterController
import com.example.tinyfarm.game.FarmMap
import com.example.tinyfarm.game.Tile
import com.example.tinyfarm.game.TileSet


class GameView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var tileSize = 64   // 64 / 48 / 32

    private val farmMap = FarmMap.map
    val controller = CharacterController()
    private val tileBitmapCache = HashMap<Int, Bitmap>()



    private var playerDown: Array<Bitmap?> = arrayOfNulls(6)
    private var playerUp: Array<Bitmap?> = arrayOfNulls(6)
    private var playerLeft: Array<Bitmap?> = arrayOfNulls(6)
    private var playerRight: Array<Bitmap?> = arrayOfNulls(6)

    // camera offset (world -> screen)
    private var cameraX = 0f
    private var cameraY = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // încarcă o singură dată (ok pentru început)
        tileBitmapCache.clear()


        playerDown = loadPlayerFrames("om_jos")
        playerUp = loadPlayerFrames("om_sus")
        playerLeft = loadPlayerFrames("om_stanga")
        playerRight = loadPlayerFrames("om_dreapta")

        // poziție inițială player (în lumea hărții): centru hartă
        val mapRows = farmMap.size
        val mapCols = farmMap[0].size
        controller.x = (mapCols * tileSize) / 2f
        controller.y = (mapRows * tileSize) / 2f

    }
    private fun getTileBitmap(resId: Int): Bitmap {
        return tileBitmapCache.getOrPut(resId) {
            val opts = BitmapFactory.Options().apply {
                inScaled = false
                inPreferredConfig = Bitmap.Config.ARGB_8888
            }
            val original = BitmapFactory.decodeResource(resources, resId, opts)
            Bitmap.createScaledBitmap(original, tileSize, tileSize, false)
        }
    }



    private fun loadPlayerFrames(prefix: String): Array<Bitmap?> {
        val arr: Array<Bitmap?> = arrayOfNulls(6)
        for (i in 1..6) {
            val id = resources.getIdentifier("$prefix$i", "drawable", context.packageName)
            if (id != 0) arr[i - 1] = getTileBitmap(id)
        }
        return arr
    }
    val mapRows = farmMap.size
    val mapCols = farmMap[0].size

    private fun stableVariantIndex(row: Int, col: Int, size: Int): Int {
        if (size <= 1) return 0
        // hash simplu, stabil, fără Random()
        val h = (row * 73856093) xor (col * 19349663)
        return (h and Int.MAX_VALUE) % size
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        // 1) camera urmărește playerul (player în centru ecran)
        cameraX = controller.x - width / 2f
        cameraY = controller.y - height / 2f

        // 2) clamp camera ca să nu iasă în afara hărții
        val mapPixelW = mapCols * tileSize
        val mapPixelH = mapRows * tileSize
        controller.x = controller.x.coerceIn(0f, (FarmMap.COLS * tileSize - tileSize).toFloat())
        controller.y = controller.y.coerceIn(0f, (FarmMap.ROWS * tileSize - tileSize).toFloat())



        cameraX = cameraX.coerceIn(0f, (mapPixelW - width).coerceAtLeast(0).toFloat())
        cameraY = cameraY.coerceIn(0f, (mapPixelH - height).coerceAtLeast(0).toFloat())

        // 3) desenează doar tile-urile vizibile
        val startCol = (cameraX / tileSize).toInt().coerceAtLeast(0)
        val startRow = (cameraY / tileSize).toInt().coerceAtLeast(0)

        val endCol = ((cameraX + width) / tileSize).toInt().coerceAtMost(mapCols - 1)
        val endRow = ((cameraY + height) / tileSize).toInt().coerceAtMost(mapRows - 1)


        for (row in startRow..endRow) {
            for (col in startCol..endCol) {
                val tile = farmMap[row][col]

                val variants = TileSet.tiles[tile] ?: continue
                val idx = stableVariantIndex(row, col, variants.size)
                val resId = variants[idx]
                val bmp = getTileBitmap(resId)

                val screenX = (col * tileSize) - cameraX
                val screenY = (row * tileSize) - cameraY
                canvas.drawBitmap(bmp, screenX, screenY, null)
            }
        }
        // 4) player (pe ecran) = world - camera
        controller.updateAnimation(System.currentTimeMillis())
        val frame = controller.getFrameIndex()

        val playerBmp = when (controller.direction) {
            CharacterController.Direction.DOWN -> playerDown[frame]
            CharacterController.Direction.UP -> playerUp[frame]
            CharacterController.Direction.LEFT -> playerLeft[frame]
            CharacterController.Direction.RIGHT -> playerRight[frame]
        } ?: return

        val px = controller.x - cameraX
        val py = controller.y - cameraY
        canvas.drawBitmap(playerBmp, px, py, null)
    }
}
