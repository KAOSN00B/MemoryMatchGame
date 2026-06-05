package com.lasallecollegevancouver.memorymatchgame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView

class CardAdapter(private val cardValues: MutableList<Int>) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>()
{
    // Tracks which cards are currently face-up
    private val flippedState = MutableList(cardValues.size) { false }

    // Holds the button view for one card cell
    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val root: FrameLayout = itemView as FrameLayout
        val cardButton: Button = itemView.findViewById(R.id.cardButton_id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder
    {
        // Inflate the card layout for each grid cell
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.tile_layout, parent, false)
        return CardViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int)
    {
        val isFlipped = flippedState[position]

        // Show the card value if flipped, otherwise show "?"
        holder.cardButton.text = if (isFlipped) cardValues[position].toString() else "?"

        holder.cardButton.setOnClickListener {
            // Flip the card and refresh only this cell
            flippedState[position] = !flippedState[position]
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = cardValues.size

    // Resets all cards face-down (called by the restart button)
    fun resetCards()
    {
        for (index in flippedState.indices)
        {
            flippedState[index] = false
        }
        notifyDataSetChanged()
    }
}
