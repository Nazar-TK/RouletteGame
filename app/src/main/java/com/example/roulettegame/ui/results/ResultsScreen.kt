package com.example.roulettegame.ui.results

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.roulettegame.R
import com.example.roulettegame.domain.model.Stake
import com.example.roulettegame.presentation.utils.UIEvent
import com.example.roulettegame.ui.game.GameEvent
import com.example.roulettegame.ui.game.GameViewModel
import com.example.roulettegame.ui.game.RechargeDialog
import com.example.roulettegame.ui.theme.Orange
import com.example.roulettegame.ui.theme.Red
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsScreen(
    onPopBackStack: () -> Unit,
    viewModel: ResultsViewModel = hiltViewModel()
) {

    val accountBalance by viewModel.stakes.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Results",
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Red,
                    titleContentColor = Orange,
                    navigationIconContentColor = Orange,
                    actionIconContentColor = Orange
                ),
                navigationIcon = {
                    IconButton(onClick = { onPopBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Go back")
                    }
                }
            )
        }
    ) { values ->

        Box(Modifier.fillMaxSize().padding(values)) {

            Image(
                painter = painterResource(
                    id = R.drawable.app_background
                ),
                contentDescription = "app background",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize()
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(accountBalance) { item ->
                    ResultsItem(item)
                }
            }
        }
    }
}

