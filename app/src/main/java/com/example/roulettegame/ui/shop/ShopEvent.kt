package com.example.roulettegame.ui.shop

import com.example.roulettegame.ui.game.GameEvent

sealed class ShopEvent{
    data object OnGoBackClick : ShopEvent()
    data object OnOpenRechargeBalanceDialogClick : ShopEvent()
    data object OnDismissRechargeBalanceDialogClick : ShopEvent()
    data class OnRechargeBalanceClick(val coinsAmount: Int) : ShopEvent()
    data object OnDismissBuyRouletteDialogClick : ShopEvent()
    data object OnBuyRouletteClick : ShopEvent()
    data class OnRouletteClick(val index: Int) : ShopEvent()
}
