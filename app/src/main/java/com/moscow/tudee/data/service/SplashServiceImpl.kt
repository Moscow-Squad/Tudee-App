package com.moscow.tudee.data.service

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.moscow.tudee.domain.service.SplashService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "splash_prefs")

class SplashServiceImpl(private val context: Context) : SplashService {
    private val onboardingShownKey = booleanPreferencesKey("onboarding_shown")

    override suspend fun markOnboardingAsShown(shown: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[onboardingShownKey] = shown
        }
    }

    override suspend fun hasSeenOnboarding(): Boolean {
        return context.dataStore.data
            .map { prefs -> prefs[onboardingShownKey] == true }
            .first()
    }
}
