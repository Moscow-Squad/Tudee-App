package com.moscow.tudee.di

import com.moscow.tudee.presentation.category.categoryScreen.CategoryViewModel
import com.moscow.tudee.presentation.category.categoryTasksScreen.CategoryTasksViewModel
import com.moscow.tudee.presentation.screen.home.HomeViewModel
import com.moscow.tudee.presentation.task.TaskViewModel
import com.moscow.tudee.presentation.ui.splash.viewmodel.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::SplashViewModel)
    viewModelOf(::TaskViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::CategoryViewModel)
    viewModel { (categoryId: Long) ->
        CategoryTasksViewModel(categoryId, get(), get())
    }
}

