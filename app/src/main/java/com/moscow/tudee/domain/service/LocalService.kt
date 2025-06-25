package com.moscow.tudee.domain.service

interface LocalService {
    suspend fun markOnboardingAsShown(shown: Boolean)
    suspend fun hasSeenOnboarding(): Boolean
    suspend fun isSystemThemeDark(): Boolean?
    suspend fun setSystemThemeDark(isDark: Boolean)
}