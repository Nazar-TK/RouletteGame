package com.example.roulettegame.ui.shop

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roulettegame.core.utils.Resource
import com.example.roulettegame.domain.model.UserData
import com.example.roulettegame.domain.repository.GameRepository
import com.example.roulettegame.domain.repository.UserDataRepository
import com.example.roulettegame.presentation.utils.UIEvent
import com.example.roulettegame.ui.game.GameEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    var showRechargeDialog = mutableStateOf(false)
        private set

    private val _accountBalanceState = MutableStateFlow(0)
    val accountBalanceState: StateFlow<Int> = _accountBalanceState

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _selectedImageIndex = MutableStateFlow(0)
    val selectedImageIndex: StateFlow<Int?> = _selectedImageIndex.asStateFlow()

    private val _unlockedImages = MutableStateFlow(listOf(true, false, false))
    val unlockedImages: StateFlow<List<Boolean>> = _unlockedImages.asStateFlow()

    var showBuyRouletteDialog = mutableStateOf(false)
        private set

    var selectedImageForPurchase: MutableState<Int?> = mutableStateOf(null)
        private set

    val roulettePrices = listOf(0, 100, 200)

    init {
        viewModelScope.launch {
            userDataRepository.getUserData().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _accountBalanceState.value = result.data?.coins ?: 0
                    }
                    is Resource.Error -> {}
                }
            }.launchIn(viewModelScope)
        }
    }

    fun onEvent(event: ShopEvent) {
        when (event) {
            is ShopEvent.OnGoBackClick -> {
                sendUiEvent(
                    UIEvent.PopBackStack
                )
            }
            is ShopEvent.OnOpenRechargeBalanceDialogClick -> {
                toggleRechargeDialog(true)
            }
            is ShopEvent.OnDismissRechargeBalanceDialogClick -> {
                toggleRechargeDialog(false)
            }
            is ShopEvent.OnRechargeBalanceClick -> {
                updateBalance(event.coinsAmount + _accountBalanceState.value)
                toggleRechargeDialog(false)
            }
            is ShopEvent.OnRouletteClick -> {
                onImageChecked(event.index)
            }
            is ShopEvent.OnBuyRouletteClick -> {
                confirmPurchase()
                toggleBuyRouletteDialog(false)
            }
            is ShopEvent.OnDismissBuyRouletteDialogClick -> {
                toggleBuyRouletteDialog(false)
            }
        }
    }

    private fun toggleRechargeDialog(show: Boolean) {
        showRechargeDialog.value = show
    }

    private fun updateBalance(newBalance: Int) {
        userDataRepository.insertUserData(UserData(newBalance)).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _accountBalanceState.value = newBalance
                }

                is Resource.Error -> {
                    sendUiEvent(
                        UIEvent.ShowSnackBar(
                            message = "Could not recharge the balance"
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)

    }

    private fun onImageChecked(index: Int) {
        if (!_unlockedImages.value[index]) {
            selectedImageForPurchase.value = index
            toggleBuyRouletteDialog(true)
        } else {
            _selectedImageIndex.value = index
        }
    }


    private fun toggleBuyRouletteDialog(show: Boolean) {
        showBuyRouletteDialog.value = show
    }
    private fun confirmPurchase() {
        selectedImageForPurchase.value?.let { index ->
            if (roulettePrices[index] <= _accountBalanceState.value) {
                val newUnlockedImages = _unlockedImages.value.toMutableList()
                newUnlockedImages[index] = true
                _unlockedImages.value = newUnlockedImages
                _selectedImageIndex.value = index
                toggleBuyRouletteDialog(false)
            } else {
                sendUiEvent(
                    UIEvent.ShowSnackBar(
                        message = "Not enough coins"
                    )
                )
            }

        }
    }

    private fun sendUiEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}