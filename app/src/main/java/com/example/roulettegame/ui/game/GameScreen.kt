package com.example.roulettegame.ui.game

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.roulettegame.R
import com.example.roulettegame.presentation.utils.UIEvent
import com.example.roulettegame.ui.commoncomposables.RechargeDialog
import com.example.roulettegame.ui.theme.Orange
import com.example.roulettegame.ui.theme.Red
import com.example.roulettegame.ui.theme.Yellow
import com.example.roulettegame.ui.theme.YellowLight
import com.example.roulettegame.ui.theme.fontFamily


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    onPopBackStack: () -> Unit,
    viewModel: GameViewModel = hiltViewModel()
) {

    val angle: Float by animateFloatAsState(
        targetValue = viewModel.rotationValue,
        animationSpec = tween(
            durationMillis = 10000
        ),
        label = ""
    )

    val accountBalance by viewModel.accountBalanceState.collectAsState()
    val showDialog by viewModel.showRechargeDialog

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UIEvent.PopBackStack -> onPopBackStack()
                is UIEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Balance: $accountBalance",
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Red,
                    titleContentColor = Orange,
                    navigationIconContentColor = Orange,
                    actionIconContentColor = Orange
                ),
                navigationIcon = {
                    IconButton(onClick = { viewModel.onEvent(GameEvent.OnGoBackClick) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Go back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.onEvent(GameEvent.OnOpenRechargeBalanceDialogClick) }) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "Recharge the balance")
                    }
                    if (showDialog) {
                        RechargeDialog(
                            onDismiss = { viewModel.onEvent((GameEvent.OnDismissRechargeBalanceDialogClick)) },
                            onRecharge = { amount ->
                                viewModel.onEvent(GameEvent.OnRechargeBalanceClick(amount))
                            }
                        )
                    }
                }
            )
        }
    ) { values ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(values)
        ) {

            Image(
                painter = painterResource(
                    id = R.drawable.app_background
                ),
                contentDescription = "app background",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize()
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(
                            id = R.drawable.roulette_1
                        ),
                        contentDescription = "Roulette",
                        modifier = Modifier
                            .fillMaxSize()
                            .rotate(angle)
                    )
                    Image(
                        painter = painterResource(
                            id = R.drawable.arrow
                        ),
                        contentDescription = "Arrow",
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Text(
                    text = stringResource(id = R.string.stake),
                    color = Yellow,
                    fontSize = 42.sp,
                    fontFamily = fontFamily,
                    fontStyle = FontStyle.Normal,
                )

                Spacer(modifier = Modifier.height(12.dp))

                TextField(
                    value = viewModel.stake,
                    onValueChange = {
                        viewModel.onEvent(GameEvent.OnStakeChange(it))
                    },
                    textStyle = TextStyle.Default.copy(
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center
                    ),
                    placeholder = {
                        Text(text = stringResource(id = R.string.your_stake))
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = YellowLight,
                        textColor = Orange,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Orange,
                        placeholderColor = Orange

                    ),
                    shape = RoundedCornerShape(18.dp),
                )

                RadioButtonGroup(viewModel)

                Button(
                    onClick = { viewModel.onEvent(GameEvent.OnRollClick(angle)) },
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                        .height(80.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Orange
                    ),
                    border = BorderStroke(5.dp, Yellow)
                ) {
                    Text(
                        text = stringResource(id = R.string.roll),
                        color = YellowLight,
                        fontSize = 40.sp,
                        fontFamily = fontFamily,
                        fontStyle = FontStyle.Normal,
                    )
                }
            }
        }
    }
}

@Composable
private fun RadioButtonGroup(viewModel: GameViewModel) {

    val options = listOf("Red", "Black", "Green")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                RadioButton(
                    selected = viewModel.color == option,
                    onClick = { viewModel.onEvent(GameEvent.OnColorSelectionChange(option)) },
                    colors = RadioButtonDefaults.colors(selectedColor = Orange)
                )
                Text(text = option, color = Orange, fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}
