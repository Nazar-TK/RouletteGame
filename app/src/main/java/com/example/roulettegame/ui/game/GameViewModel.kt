package com.example.roulettegame.ui.game

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roulettegame.core.utils.Resource
import com.example.roulettegame.domain.model.UserData
import com.example.roulettegame.domain.repository.GameRepository
import com.example.roulettegame.domain.repository.UserDataRepository
import com.example.roulettegame.presentation.utils.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameRepository: GameRepository,
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private val TAG = "GameViewModel"

    var stake by mutableStateOf("")
        private set

    var color by mutableStateOf("Red")
        private set

    var rotationValue by mutableStateOf(0f)
        private set

    var showRechargeDialog = mutableStateOf(false)
        private set

    private val _accountBalanceState = MutableStateFlow(0)
    val accountBalanceState: StateFlow<Int> = _accountBalanceState


    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

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

    fun onEvent(event: GameEvent) {
        when (event) {
            is GameEvent.OnStakeChange -> {
                stake = event.stake
            }

            is GameEvent.OnColorSelectionChange -> {
                color = event.color
            }

            is GameEvent.OnGoBackClick -> {
                sendUiEvent(
                    UIEvent.PopBackStack
                )
            }

            is GameEvent.OnOpenRechargeBalanceDialogClick -> {
                toggleRechargeDialog(true)
            }

            is GameEvent.OnDismissRechargeBalanceDialogClick -> {
                toggleRechargeDialog(false)
            }

            is GameEvent.OnRechargeBalanceClick -> {
                Log.d(TAG, "OnRechargeBalanceClick")
                updateBalance(event.coinsAmount + _accountBalanceState.value)
                toggleRechargeDialog(false)
            }

            is GameEvent.OnRollClick -> {
                viewModelScope.launch {
                    if (stake.isBlank()) {
                        sendUiEvent(
                            UIEvent.ShowSnackBar(
                                message = "Make a bet"
                            )
                        )
                        return@launch
                    } else if (_accountBalanceState.value < stake.toInt()) {
                        sendUiEvent(
                            UIEvent.ShowSnackBar(
                                message = "Not enough coins"
                            )
                        )
                        return@launch
                    }
                    rotationValue = (720..1440).random().toFloat() + event.angle
                    delay(10000L)
                    gameRepository.rollTheRoulette(stake.toInt(), color, rotationValue)
                        .onEach { result ->
                            when (result) {
                                is Resource.Success -> {

                                    val newBalance: Int
                                    if (result.data!!.second.isAWinningBet) {
                                        newBalance =
                                            _accountBalanceState.value + (result.data.second.winningAmount
                                                ?: 0)
                                        updateBalance(newBalance)
                                    } else {
                                        newBalance = _accountBalanceState.value - (result.data.second.stake)
                                        updateBalance(newBalance)
                                    }
                                    _accountBalanceState.value = newBalance
                                    Log.d(
                                        TAG,
                                        "rollTheRoulette(): user won = ${result.data.second.isAWinningBet}"
                                    )
                                }

                                is Resource.Error -> {
                                    Log.d(TAG, result.message.toString())
                                }
                            }
                        }
                        .launchIn(viewModelScope)
                }
            }
        }
    }

    private fun toggleRechargeDialog(show: Boolean) {
        showRechargeDialog.value = show
    }

    private fun updateBalance(newBalance: Int) {
        Log.d(TAG, "updateBalance")
        //viewModelScope.launch {
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

    private fun sendUiEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}