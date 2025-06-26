package com.moscow.tudee.presentation.screen.onboarding.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.moscow.tudee.R
import com.moscow.tudee.presentation.screen.onboarding.OnboardingData

@Composable
fun provideOnboardingPages(): List<OnboardingData> = listOf(
    OnboardingData(
        imageRes = R.drawable.image_container_1,
        title = stringResource(R.string.overwhelmed_with_tasks),
        description = stringResource(R.string.let_s_bring_some_order_to_the_chaos_tudee_is_here_to_help_you_sort_plan_and_breathe_easier)
    ),
    OnboardingData(
        imageRes = R.drawable.image_container_3,
        title = stringResource(R.string.uh_oh_procrastinating_again),
        description = stringResource(R.string.tudee_not_mad_just_a_little_disappointed)
    ),
    OnboardingData(
        imageRes = R.drawable.image_container_2,
        title = stringResource(R.string.let_s_complete_tasks_and_celebrate_together),
        description = stringResource(R.string.tudee_will_celebrate_you_on_every_win)
    )
)