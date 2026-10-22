package com.example.dashboardscren.di

import com.example.dashboardscren.data.datasource.DashboardLocalDataSource
import com.example.dashboardscren.data.datasource.DashboardLocalDataSourceImpl
import com.example.dashboardscren.data.repository.DashboardRepositoryImpl
import com.example.core.domain.repository.DashboardRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DashboardModule {

    @Binds
    abstract fun bindDashboardLocalDataSource(
        dashboardImpl: DashboardLocalDataSourceImpl
    ): DashboardLocalDataSource

    @Binds
    abstract fun bindDashboardRepository(
        repository: DashboardRepositoryImpl,
    ): DashboardRepository
}
