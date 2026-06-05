package com.lasallecollegevancouver.memorymatchgame

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView

enum class TileState { HIDDEN, FLIPPED, MATCHED }

class Tile(context: Context, var number : Int): AppCompatTextView(context)
{
    var state: TileState = TileState.HIDDEN
}

class AppData
{
    companion object
    {
        var gridSize = 4 

        var tilesArray: ArrayList<Tile> = arrayListOf()

        fun createTiles(context: Context)
        {
           tilesArray.clear()
           for (i in 1.. (gridSize * gridSize))
           {
               val num = i % ((gridSize * gridSize) / 2) + 1
               val tile = Tile(context, num)
               tilesArray.add(tile)
           }
            tilesArray.shuffle()
        }
    }
}

class MainActivity : AppCompatActivity() {

    lateinit var restartButton: Button
    lateinit var gameViewRV: RecyclerView
    lateinit var statusText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        AppData.createTiles(this)
        cacheOutlet() // wire up views after the layout is set
    }
}
