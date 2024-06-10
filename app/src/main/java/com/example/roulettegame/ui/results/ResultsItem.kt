package com.example.roulettegame.ui.results

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roulettegame.R
import com.example.roulettegame.domain.model.Stake
import com.example.roulettegame.ui.theme.Green
import com.example.roulettegame.ui.theme.Orange
import com.example.roulettegame.ui.theme.OrangeDark
import com.example.roulettegame.ui.theme.Red
import com.example.roulettegame.ui.theme.RedLight
import com.example.roulettegame.ui.theme.Yellow
import com.example.roulettegame.ui.theme.fontFamily
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ResultsItem(stake: Stake) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(4.dp, Orange),
        colors = CardDefaults.cardColors(
            containerColor = Yellow
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = formatLocalDateTime(stake.date),
                color = OrangeDark,
                fontSize = 24.sp,
                fontFamily = fontFamily,
                fontStyle = FontStyle.Normal,
            )
            Row(modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Row {
                        Text(
                            text = "Bet: ",
                            color = Red,
                            fontSize = 20.sp,
                            fontFamily = fontFamily,
                            fontStyle = FontStyle.Normal,
                        )
                        Text(
                            text = "${stake.stake} coins",
                            color = OrangeDark,
                            fontSize = 20.sp,
                            fontFamily = fontFamily,
                            fontStyle = FontStyle.Normal,
                        )
                    }
                    Row {
                        Text(
                            text = "Bet color: ",
                            color = Red,
                            fontSize = 20.sp,
                            fontFamily = fontFamily,
                            fontStyle = FontStyle.Normal,
                        )
                        Text(
                            text = stake.color,
                            color = OrangeDark,
                            fontSize = 20.sp,
                            fontFamily = fontFamily,
                            fontStyle = FontStyle.Normal,
                        )
                    }
                }
                if (stake.isAWinningBet){
                    Text(
                        text = "Result: +${stake.winningAmount} coins",
                        color = Green,
                        fontSize = 20.sp,
                        fontFamily = fontFamily,
                        fontStyle = FontStyle.Normal,
                    )
                } else {
                    Text(
                        text = "Result: -${stake.stake} coins",
                        color = RedLight,
                        fontSize = 20.sp,
                        fontFamily = fontFamily,
                        fontStyle = FontStyle.Normal,
                    )
                }

            }
        }

    }
}

// Utility function to format LocalDateTime
fun formatLocalDateTime(localDateTime: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM HH:mm", Locale.ENGLISH)
    return localDateTime.format(formatter)
}