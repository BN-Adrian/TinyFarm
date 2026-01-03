package com.example.tinyfarm.game
import com.example.tinyfarm.R


enum class Tile {
    GRASS,
    DIRT,
    PATH30,
    PATH31,
    PATH32,
    PATH39,
    PATH40,
    PATH41,
    PATH42,
    PATH44

}




object TileSet {

    val tiles: Map<Tile, IntArray> = mapOf(
        Tile.GRASS to intArrayOf(
            R.drawable.fieldstile_38
        ),
        Tile.DIRT to intArrayOf(
            R.drawable.fieldstile_20,
            R.drawable.fieldstile_23,
            R.drawable.fieldstile_25,
            R.drawable.fieldstile_27,
            R.drawable.fieldstile_36,
            R.drawable.fieldstile_57,
            R.drawable.fieldstile_58,
            R.drawable.fieldstile_59,
            R.drawable.fieldstile_64,

        ),
        Tile.PATH30 to intArrayOf(
            R.drawable.fieldstile_30
        ),
        Tile.PATH31 to intArrayOf(
            R.drawable.fieldstile_31
        ),
        Tile.PATH32 to intArrayOf(
            R.drawable.fieldstile_32
        ),
        Tile.PATH39 to intArrayOf(
            R.drawable.fieldstile_39
        ),
        Tile.PATH40 to intArrayOf(
            R.drawable.fieldstile_40
        ),
        Tile.PATH41 to intArrayOf(
            R.drawable.fieldstile_41
        ),
        Tile.PATH42 to intArrayOf(
            R.drawable.fieldstile_42
        ),
        Tile.PATH44 to intArrayOf(
            R.drawable.fieldstile_44
        )
    )
}