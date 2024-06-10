package com.example.roulettegame.ui.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roulettegame.core.utils.Resource
import com.example.roulettegame.domain.model.Stake
import com.example.roulettegame.domain.repository.GameRepository
import com.example.roulettegame.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ResultsViewModel @Inject constructor(
    private val gameRepository: GameRepository
) : ViewModel() {

    private val _stakes = MutableStateFlow<List<Stake>>(emptyList())
    val stakes: StateFlow<List<Stake>> = _stakes

    init {
        getStakes()
    }
    fun getStakes() {

        gameRepository.getStakes().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _stakes.value = result.data ?: emptyList()
                }
                is Resource.Error -> {}
            }
        }.launchIn(viewModelScope)
    }
}