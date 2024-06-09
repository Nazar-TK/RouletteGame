package com.example.roulettegame.ui.game

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roulettegame.domain.GameRepository
import com.example.roulettegame.presentation.utils.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val repository: GameRepository
): ViewModel() {

    var stake by mutableStateOf("")
        private set

    var color by mutableStateOf("Red")
        private set

    var rotationValue by mutableStateOf(0f)
        private set

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
                    val res = repository.rollTheRoulette(stake.toInt(), color, rotationValue)


                    Log.d("HERE! rotationValue: ", rotationValue.toString())
                    Log.d("HERE! result: ", res)
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