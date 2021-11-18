package com.ecommerce.di

import com.ecommerce.network.ApiService
import com.ecommerce.repository.ListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
object RepositoryModule {

    /**
     * Repositories
     */
    @Provides
    fun providesRepo(apiService: ApiService): ListRepository {
        return ListRepository(apiService = apiService)
    }
}