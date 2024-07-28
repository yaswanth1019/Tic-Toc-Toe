package com.example.tictoctoe.ui

import androidx.compose.ui.graphics.Color
import com.example.tictoctoe.R

data class GameUiState (
    val board:List<Int> = List(9){0},
    val currentPlayer: Int = R.drawable.tic, // Resource ID for 'X'
    val winsColor:Color = Color(0,208,117,255),
    val winStatus:Boolean=false,
    val winElement:List<Int> = emptyList(),
    val xPoints:Int=0,
    val oPoints:Int=0,
    val gameStatus:String=""
    )