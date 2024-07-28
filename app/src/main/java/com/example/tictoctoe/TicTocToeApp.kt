package com.example.tictoctoe

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tictoctoe.ui.GameModel
import com.example.tictoctoe.ui.GameScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicTocToeApp(){
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val gameModel:GameModel= viewModel()
    val gameUiState by gameModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) {

        GameScreen(gameModel=gameModel,
            gameUiState=gameUiState,
            contentPadding=it)
    }
}