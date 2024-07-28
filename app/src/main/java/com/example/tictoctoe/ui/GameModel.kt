package com.example.tictoctoe.ui

import androidx.lifecycle.ViewModel
import com.example.tictoctoe.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class GameModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState

    init {
        restartGame()
    }

    fun onCellClicked(row: Int, col: Int) {
        val index = row * 3 + col
        if (_uiState.value.board[index] == 0 && !_uiState.value.winStatus) {
            updateBoard(index)
            calculateWinStatus()
        }
    }

    private fun updateBoard(index: Int) {
        _uiState.update { currentState ->
            val updatedBoard = currentState.board.toMutableList().apply {
                this[index] = currentState.currentPlayer
            }
            currentState.copy(
                board = updatedBoard,
                currentPlayer = if (currentState.currentPlayer == R.drawable.tic) R.drawable.toc else R.drawable.tic
            )
        }
    }

    private fun calculateWinStatus() {
        val board = _uiState.value.board
        val winPatterns = listOf(
            listOf(0, 1, 2), // Row 1
            listOf(3, 4, 5), // Row 2
            listOf(6, 7, 8), // Row 3
            listOf(0, 3, 6), // Column 1
            listOf(1, 4, 7), // Column 2
            listOf(2, 5, 8), // Column 3
            listOf(0, 4, 8), // Diagonal 1
            listOf(2, 4, 6)  // Diagonal 2
        )

        for (pattern in winPatterns) {
            if (board[pattern[0]] != 0 && board[pattern[0]] == board[pattern[1]] && board[pattern[1]] == board[pattern[2]]) {
                _uiState.update { currentState ->
                    currentState.copy(
                        winStatus = true,
                        winElement = pattern
                    )
                }
                updatePoints(board[pattern[0]])
                return
            }
        }
        if (board.all { it != 0 }) {
            _uiState.update { currentState ->
                currentState.copy(gameStatus = "Draw")
            }
        }
    }

    private fun updatePoints(winner: Int) {
        _uiState.update { currentState ->
            when (winner) {
                R.drawable.tic -> currentState.copy(
                    xPoints = currentState.xPoints + 1,
                    gameStatus = "Player X won"
                )
                R.drawable.toc -> currentState.copy(
                    oPoints = currentState.oPoints + 1,
                    gameStatus = "Player O won"
                )
                else -> currentState.copy(gameStatus = "Draw")
            }
        }
    }

    fun restartGame() {
        _uiState.update {
            GameUiState()
        }
    }

    fun replyGame(){
        _uiState.update { currentState ->
            currentState.copy(
                board = List(9) { 0 },
                currentPlayer = R.drawable.tic, // Resource ID for 'X'
                winStatus = false,
                winElement = emptyList(),
                gameStatus = ""
            )
        }
    }
}
