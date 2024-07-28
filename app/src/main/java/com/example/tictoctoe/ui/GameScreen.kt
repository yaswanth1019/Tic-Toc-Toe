package com.example.tictoctoe.ui

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tictoctoe.R

@Composable
fun GameScreen(
    gameModel: GameModel,
    gameUiState: GameUiState,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val context = LocalContext.current


    if (gameUiState.winStatus || gameUiState.gameStatus == "Draw") {
        EndGameDialog(
            gameStatus = gameUiState.gameStatus,
            onReplay = { gameModel.replyGame() },
            onExit = { (context as Activity).finish() }
        )
    }


    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ){
        ScoreBoards(gameUiState = gameUiState, modifier = Modifier.padding(10.dp))
        PlayingBoard(gameModel = gameModel, gameUiState = gameUiState)
        Restart(gameModel=gameModel)
    }
}

@Composable
fun ScoreBoards(gameUiState: GameUiState, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ScoreBox(
            playerLabel = "Player 1",
            imageResId = R.drawable.tic,
            points = gameUiState.xPoints
        )
        ScoreBox(
            playerLabel = "Player 2",
            imageResId = R.drawable.toc,
            points = gameUiState.oPoints
        )
    }
}


@Composable
fun ScoreBox(playerLabel: String, imageResId: Int, points: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color(240, 240, 240), shape = RoundedCornerShape(10.dp))
            .padding(14.dp)
    ) {
        Text(text = playerLabel, color = Color.Black)
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            modifier = Modifier.size(45.dp)
        )
        Text(text = points.toString(), color = Color.Black, fontSize = 30.sp)
    }
}

@Composable
fun PlayingBoard(
    gameModel: GameModel,
    gameUiState: GameUiState,
) {
    val boardSize = 3
    LazyVerticalGrid(
        columns = GridCells.Fixed(boardSize),
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        items(boardSize * boardSize) { index ->
            val row = index / boardSize
            val col = index % boardSize
            val cellValue = gameUiState.board[index]
            val isWinningCell = index in gameUiState.winElement

            TicTocToeCell(
                value = cellValue,
                onClick = { gameModel.onCellClicked(row, col) },
                isWinningCell = isWinningCell,
                winsColor = gameUiState.winsColor,
                modifier = Modifier
                    .aspectRatio(1f)
                    .padding(10.dp),
            )
        }
    }
}

@Composable
fun TicTocToeCell(
    value: Int,
    onClick: () -> Unit,
    isWinningCell: Boolean,
    winsColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clickable(onClick = onClick)
            .background(
                if (isWinningCell) winsColor else Color(157, 203, 255, 255),
                shape = RoundedCornerShape(10)
            )
            .fillMaxSize()
    ) {
        if (value != 0) { // Only display the image if the cell is not empty
            Image(
                painter = painterResource(id = value),
                contentDescription = "",
                modifier = Modifier.size(55.dp)
            )
        }
    }
}

@Composable
fun Restart(gameModel: GameModel){
    Button(onClick = { gameModel.restartGame()},
        modifier = Modifier.padding(16.dp)) {
        Text(text = "Restart")
    }
}

@Composable
fun ShowEndGameDialog(
    gameStatus: String,
    onReplay: () -> Unit,
    onExit: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = "Game Over") },
        text = { Text(text = gameStatus) },
        confirmButton = {
            Button(onClick = onReplay) {
                Text(text = "Replay")
            }
        },
        dismissButton = {
            Button(onClick = onExit) {
                Text(text = "Exit")
            }
        }
    )
}

@Composable
fun EndGameDialog(
    gameStatus: String,
    onReplay: () -> Unit,
    onExit: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = "\uD83C\uDF89 Game Over \uD83C\uDF89") },
        text = { Text(text = "\uD83C\uDF89 $gameStatus \uD83C\uDF89") },
        confirmButton = {
            Button(onClick = onReplay) {
                Text(text = "Replay")
            }
        },
        dismissButton = {
            Button(onClick = onExit) {
                Text(text = "Exit")
            }
        }
    )
}
