package com.example.roulettegame.ui.game

sealed class GameEvent {

    data class OnStakeChange(val stake: String) : GameEvent()
    data class OnColorSelectionChange(val color: String) : GameEvent()
    data object OnRollClick : GameEvent()
}