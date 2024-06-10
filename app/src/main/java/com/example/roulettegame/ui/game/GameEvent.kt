package com.example.roulettegame.ui.game

sealed class GameEvent {

    data class OnStakeChange(val stake: String) : GameEvent()
    data class OnColorSelectionChange(val color: String) : GameEvent()
    data class OnRollClick(val angle: Float) : GameEvent()
    data object OnGoBackClick : GameEvent()
    data object OnOpenRechargeBalanceDialogClick : GameEvent()
    data object OnDismissRechargeBalanceDialogClick : GameEvent()
    data class OnRechargeBalanceClick(val coinsAmount: Int) : GameEvent()

}