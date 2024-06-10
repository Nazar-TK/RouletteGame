package com.example.roulettegame.ui.game

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roulettegame.core.utils.Resource
import com.example.roulettegame.domain.repository.GameRepository
import com.example.roulettegame.presentation.utils.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repository: GameRepository
): ViewModel() {

    private val TAG = "GameViewModel"

    var stake by mutableStateOf("")
        private set

    var color by mutableStateOf("Red")
        private set

    var rotationValue by mutableStateOf(0f)
        private set

    private val _accountBalanceState = MutableStateFlow("0.0")
    val accountBalanceState: StateFlow<String> = _accountBalanceState

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: GameEvent) {
        when (event) {
            is GameEvent.OnStakeChange -> {
                stake = event.stake
            }

            is GameEvent.OnColorSelectionChange -> {
                color = event.color
            }

            is GameEvent.OnRollClick -> {
                viewModelScope.launch {
                    if (stake.isBlank()) {
                        sendUiEvent(
                            UIEvent.ShowSnackBar(
                                message = "You need to make a bet"
                            )
                        )
                        return@launch
                    }
                    rotationValue = (720..1440).random().toFloat() + event.angle
                    repository.rollTheRoulette(stake.toInt(), color, rotationValue).onEach { result ->
                        when (result) {
                            is Resource.Success -> {
                                if( result.data!!.second) {
                                    sendUiEvent(
                                        UIEvent.ShowSnackBar(
                                            message = "Congratulations, you won!"
                                        )
                                    )
                                } else {
                                    sendUiEvent(
                                        UIEvent.ShowSnackBar(
                                            message = "Better luck next time"
                                        )
                                    )
                                }

                                Log.d(TAG, "rollTheRoulette(): user won = ${result.data?.second.toString()}")
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

    private fun sendUiEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}