package com.lasallecollegevancouver.memorymatchgame

import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun MainActivity.cacheOutlet()
{
    restartButton = findViewById(R.id.button)
    restartButton.setOnClickListener(restart())
    statusText = findViewById(R.id.memoryMatchText_id)
    gameViewRV = findViewById(R.id.recyclerGameView_id)
    gameViewRV.layoutManager = GridLayoutManager(this, AppData.gridSize)
    gameViewRV.adapter = GameAdapter { statusText.text = "You Win!" }
}

class TileViewHolder(val root: FrameLayout) : RecyclerView.ViewHolder(root)

class GameAdapter(private val onWin: () -> Unit) : RecyclerView.Adapter<TileViewHolder>()
{
    private var firstFlipped: Tile? = null
    private var isChecking = false // prevents tapping while mismatch delay is running

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TileViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tile_layout,
            parent, false) as FrameLayout
        return TileViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: TileViewHolder, index: Int)
    {
        val tile = AppData.tilesArray[index]
        val root = viewHolder.root

        // Ensure the tile is removed from any previous parent before adding it to the new one
        (tile.parent as? ViewGroup)?.removeView(tile)
        root.removeAllViews()

        // Layout params with 5dp margin and white background
        val margin = 5
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        ).apply {
            setMargins(margin, margin, margin, margin)
        }

        tile.setBackgroundColor(Color.WHITE)
        tile.layoutParams = params

        root.addView(tile)

        // Show correct text based on current state
        tile.text = when (tile.state)
        {
            TileState.HIDDEN  -> "?"
            TileState.FLIPPED -> tile.number.toString()
            TileState.MATCHED -> "✓"
        }

        tile.textSize = 34f
        tile.gravity = Gravity.CENTER

        tile.setOnClickListener {
            // Ignore taps on already-revealed cards or while a mismatch is being checked
            if (isChecking || tile.state != TileState.HIDDEN) return@setOnClickListener

            tile.state = TileState.FLIPPED
            tile.text = tile.number.toString()

            if (firstFlipped == null)
            {
                firstFlipped = tile
            }
            else
            {
                val first = firstFlipped!!
                firstFlipped = null

                if (first.number == tile.number)
                {
                    // Match — show checkmark on both
                    first.state = TileState.MATCHED
                    tile.state = TileState.MATCHED
                    first.text = "✓"
                    tile.text = "✓"
                    // Check if every tile is matched — if so the game is over
                    if (AppData.tilesArray.all { it.state == TileState.MATCHED }) onWin()
                }
                else
                {
                    // No match — flip both back after 1 second
                    isChecking = true
                    Handler(Looper.getMainLooper()).postDelayed({
                        first.state = TileState.HIDDEN
                        tile.state = TileState.HIDDEN
                        first.text = "?"
                        tile.text = "?"
                        isChecking = false
                    }, 500)
                }
            }
        }
    }

    override fun getItemCount() = AppData.tilesArray.size

    fun resetCards()
    {
        for (tile in AppData.tilesArray) {
            tile.state = TileState.HIDDEN
            tile.text = "?"
        }
        firstFlipped = null
        isChecking = false
        notifyDataSetChanged()
    }
}
