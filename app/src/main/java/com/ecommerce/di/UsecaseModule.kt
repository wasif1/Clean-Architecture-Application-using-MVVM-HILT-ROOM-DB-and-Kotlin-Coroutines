package com.ecommerce.di

import com.ecommerce.repository.ListRepository
import com.ecommerce.usecase.ListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
object UsecaseModule {

    /**
     * UseCases
     */
    @Provides
    fun providesDataUsecase(repository: ListRepository): ListUseCase {
        return ListUseCase(repository)
    }
}