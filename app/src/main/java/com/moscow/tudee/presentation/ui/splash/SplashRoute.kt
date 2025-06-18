package com.moscow.tudee.presentation.ui.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavGraphBuilder
import com.moscow.tudee.navigation.entry.SplashScreen
import com.moscow.tudee.navigation.extensions.tudeeComposable


fun NavGraphBuilder.splashRoute(
    navigateToOnBoarding:()-> Unit,
    navigateToHome:()-> Unit,
) {


    tudeeComposable<SplashScreen> {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Splash screen",
                fontWeight = FontWeight.Bold
            )
            Button(
                onClick = {
                    // @Todo check whether it's first time on Tudee or not to show on boarding
                   navigateToOnBoarding()
                }
            ) {
                Text(
                    text = "On boarding screen",
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }


}