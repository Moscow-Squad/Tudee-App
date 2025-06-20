package com.moscow.tudee.di

import com.moscow.tudee.presentation.task.TaskViewModel
import com.moscow.tudee.presentation.ui.splash.viewmodel.SplashViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::SplashViewModel)
    viewModelOf(::TaskViewModel)
}
