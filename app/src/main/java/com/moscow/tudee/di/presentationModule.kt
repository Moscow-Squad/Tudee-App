package com.moscow.tudee.di

import com.moscow.tudee.presentation.viewmodel.SplashViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::SplashViewModel)
}
