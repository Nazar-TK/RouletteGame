package com.example.roulettegame.ui.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.roulettegame.R
import com.example.roulettegame.presentation.utils.UIEvent
import com.example.roulettegame.ui.commoncomposables.RechargeDialog
import com.example.roulettegame.ui.theme.Orange
import com.example.roulettegame.ui.theme.Red

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreen(
    onPopBackStack: () -> Unit,
    viewModel: ShopViewModel = hiltViewModel()
) {

    val accountBalance by viewModel.accountBalanceState.collectAsState()
    val showRechargeDialog by viewModel.showRechargeDialog
    val showBuyRouletteDialog by viewModel.showBuyRouletteDialog

    val selectedImageForPurchase by viewModel.selectedImageForPurchase
    val selectedImageIndex by viewModel.selectedImageIndex.collectAsState()
    val unlockedImages by viewModel.unlockedImages.collectAsState()

    val imageResources = listOf(
        painterResource(id = R.drawable.roulette_1),
        painterResource(id = R.drawable.roulette_2),
        painterResource(id = R.drawable.roulette_3)
    )




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
                    IconButton(onClick = { viewModel.onEvent(ShopEvent.OnGoBackClick) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.onEvent(ShopEvent.OnOpenRechargeBalanceDialogClick) }) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "Recharge the balance"
                        )
                    }
                    if (showRechargeDialog) {
                        RechargeDialog(
                            onDismiss = { viewModel.onEvent((ShopEvent.OnDismissRechargeBalanceDialogClick)) },
                            onRecharge = { amount ->
                                viewModel.onEvent(ShopEvent.OnRechargeBalanceClick(amount))
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

            Column(modifier = Modifier.padding(16.dp)) {
                imageResources.forEachIndexed { index, painter ->
                    ImageCard(
                        painter = painter,
                        isSelected = selectedImageIndex == index,
                        isLocked = !unlockedImages[index],
                        price = viewModel.roulettePrices[index],
                        onCheckedChange = { viewModel.onEvent(ShopEvent.OnRouletteClick(index)) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            if (showBuyRouletteDialog) {
                PurchaseDialog(
                    price = viewModel.roulettePrices[selectedImageForPurchase ?: 0],
                    onDismiss = { viewModel.onEvent(ShopEvent.OnDismissBuyRouletteDialogClick) },
                    onConfirm = { viewModel.onEvent(ShopEvent.OnBuyRouletteClick) }
                )
            }
        }
    }
}

@Composable
fun ImageCard(
    painter: Painter,
    isSelected: Boolean,
    isLocked: Boolean,
    price: Int,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .toggleable(
                    value = isSelected,
                    onValueChange = onCheckedChange
                )
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Checkbox(
                checked = isSelected,
                onCheckedChange = null,
                modifier = Modifier.align(Alignment.TopEnd).padding(8.dp),
                enabled = !isLocked
            )
            if (isLocked) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.6f))
                        .align(Alignment.Center)
                ) {
                    Text(
                        text = "Unlock for $price coins",
                        color = Red,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun PurchaseDialog(price: Int, onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirm Purchase") },
        text = { Text("Unlock this image for $price coins?") },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


