# Memory Match Game

A simple Android memory matching game built with Kotlin.

## How to Play

- Tap any card to flip it and reveal its number
- Tap a second card to try to find a match
- If the two cards match, they stay flipped and show a checkmark
- If they don't match, both cards flip back face down after a short delay
- Match all pairs to win

- <img width="704" height="1486" alt="Recording 2026-06-05 161548" src="https://github.com/user-attachments/assets/94d2ab4a-4918-4aa4-9191-a9e7d624c71f" />


## Features

- 4x4 grid of shuffled card pairs
- Cards track three states: Hidden, Flipped, and Matched
- "You Win!" displayed when all pairs are found
- Restart button reshuffles and resets the board

## Built With

- Kotlin
- Android RecyclerView with a custom GameAdapter
- FrameLayout-based tile views
