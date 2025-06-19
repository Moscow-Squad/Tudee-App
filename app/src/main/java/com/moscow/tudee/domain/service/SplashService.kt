package com.moscow.tudee.domain.service

interface SplashService {
    suspend fun markOnboardingAsShown(shown: Boolean)
    suspend fun hasSeenOnboarding(): Boolean
}