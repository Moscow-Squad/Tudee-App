package com.moscow.tudee.presentation.ui.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavGraphBuilder
import com.moscow.tudee.navigation.entry.OnBoardingScreen
import com.moscow.tudee.navigation.extensions.tudeeComposable

fun NavGraphBuilder.onBoardingRoute(
    navigateToHome:()-> Unit
){
    tudeeComposable<OnBoardingScreen>{

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "On boarding  screen",
                fontWeight = FontWeight.Bold
            )
            Button(
                onClick = {
                   navigateToHome()
                }
            ) {
                Text(
                    text = "Home screen",
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
}