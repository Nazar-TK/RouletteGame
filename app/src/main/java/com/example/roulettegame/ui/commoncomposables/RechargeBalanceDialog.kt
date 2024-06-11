package com.example.roulettegame.ui.commoncomposables

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RechargeDialog(onDismiss: () -> Unit, onRecharge: (Int) -> Unit) {
    var amountText by remember { mutableStateOf(TextFieldValue()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Recharge Balance") },
        text = {
            OutlinedTextField(
                value = amountText,
                onValueChange = { amount ->
                    val filteredValue = amount.copy(text = amount.text.filter { it.isDigit() })
                    amountText = filteredValue
                },
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        },
        confirmButton = {
            Button(onClick = {
                val amount = amountText.text.toIntOrNull()
                if (amount != null) {
                    onRecharge(amount)
                }
            }) {
                Text("Recharge")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}