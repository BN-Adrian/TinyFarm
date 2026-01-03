package com.example.tinyfarm.game

object FarmMap {

    const val ROWS = 40
    const val COLS = 40

    private val rawMap = List(ROWS) { row ->
        List(COLS) { col ->
            when {
                // drum vertical
                col == 19 || col == 20 -> "30"

                // drum orizontal
                row == 19 || row == 20 -> "30"

                // zonă fermă stânga jos
                row > 22 && col < 15 -> "D"

                // zonă fermă dreapta sus
                row < 15 && col > 24 -> "D"

                else -> "G"
            }
        }.joinToString(" ")
    }

    val map: Array<Array<Tile>> = Array(ROWS) { r ->
        val tokens = rawMap[r].split(" ")
        Array(COLS) { c ->
            when (tokens[c]) {
                "G" -> Tile.GRASS
                "D" -> Tile.DIRT
                "30" -> Tile.PATH30
                else -> Tile.GRASS
            }
        }
    }
}
