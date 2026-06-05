package com.lasallecollegevancouver.memorymatchgame

import android.view.View

fun MainActivity.restart(): View.OnClickListener
{
    return View.OnClickListener {
        // Re-create and shuffle tiles, then reset the adapter and title
        AppData.createTiles(this)
        (gameViewRV.adapter as? GameAdapter)?.resetCards()
        statusText.text = "Memory Match!"
    }
}
