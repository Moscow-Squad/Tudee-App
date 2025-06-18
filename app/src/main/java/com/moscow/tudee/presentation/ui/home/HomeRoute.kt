package com.moscow.tudee.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavGraphBuilder
import com.moscow.tudee.presentation.navigation.entry.HomeScreen
import com.moscow.tudee.presentation.navigation.extensions.tudeeComposable

fun NavGraphBuilder.homeRoute() {

    tudeeComposable<HomeScreen> {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Home screen",
                fontWeight = FontWeight.Bold
            )
        }
    }
}